package com.example.britishtime.api;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.exception.GlobalExceptionHandler;
import com.example.britishtime.factory.TimeServiceFactory;
import com.example.britishtime.model.LocaleType;
import com.example.britishtime.service.TimeConversionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.annotation.Resource;

import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BritishSpokenTimeController.class)
@Import(GlobalExceptionHandler.class) // include your error DTO mapping
class BritishSpokenTimeControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private TimeServiceFactory timeServiceFactory;

    @MockBean
    private TimeConversionService timeConversionService;

    @Test
    @DisplayName("Returns 200 with SpokenTimeResponseDto for valid time")
    void spokenTime_success() throws Exception {
        when(timeServiceFactory.getService(eq(LocaleType.fromString("british").getKey())))
                .thenReturn(timeConversionService);

        when(timeConversionService.toSpokenTime(LocalTime.of(13, 45)))
                .thenReturn(new SpokenTimeResponse("13:45", "quarter to two"));

        mockMvc.perform(get("/api/time/spoken")
                        .param("time", "13:45")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.input").value("13:45"))
                .andExpect(jsonPath("$.spokenTime").value("quarter to two"));

        // verify correct factory/service usage
        verify(timeServiceFactory, times(1)).getService("british");
        ArgumentCaptor<LocalTime> captor = ArgumentCaptor.forClass(LocalTime.class);
        verify(timeConversionService, times(1)).toSpokenTime(captor.capture());
        // ensure controller parsed correctly
        LocalTime passed = captor.getValue();
        org.junit.jupiter.api.Assertions.assertEquals(LocalTime.of(13, 45), passed);
    }

    @Test
    @DisplayName("Edge times: 00:00, 12:00, 23:59 map via service")
    void spokenTime_edgeTimes() throws Exception {
        when(timeServiceFactory.getService("british")).thenReturn(timeConversionService);

        when(timeConversionService.toSpokenTime(LocalTime.of(0, 0)))
                .thenReturn(new SpokenTimeResponse("00:00", "midnight"));
        when(timeConversionService.toSpokenTime(LocalTime.of(12, 0)))
                .thenReturn(new SpokenTimeResponse("12:00", "noon"));
        when(timeConversionService.toSpokenTime(LocalTime.of(23, 59)))
                .thenReturn(new SpokenTimeResponse("23:59", "one to midnight"));

        mockMvc.perform(get("/api/time/spoken").param("time", "00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spokenTime").value("midnight"));

        mockMvc.perform(get("/api/time/spoken").param("time", "12:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spokenTime").value("noon"));

        mockMvc.perform(get("/api/time/spoken").param("time", "23:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spokenTime").value("one to midnight"));
    }

    @Test
    @DisplayName("Invalid time format -> 400 with Error DTO (IllegalArgumentException handled)")
    void invalidTime_returnsBadRequestErrorDto() throws Exception {
        mockMvc.perform(get("/api/time/spoken").param("time", "25:99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message", containsString("Invalid time format. Expected HH:mm (e.g., 10:30)"))); // depends on your message

        verifyNoInteractions(timeServiceFactory, timeConversionService);
    }

    @Test
    @DisplayName("Missing required param -> 400; optionally map with a handler to Error DTO")
    void missingParam_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/time/spoken"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message", containsString("Missing required parameter: time")));
    }

    @Test
    @DisplayName("Service throws unexpected exception -> 500 with Error DTO")
    void serviceThrows_returnsInternalServerError() throws Exception {
        when(timeServiceFactory.getService("british")).thenReturn(timeConversionService);
        when(timeConversionService.toSpokenTime(any()))
                .thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/api/time/spoken").param("time", "10:15"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.message", containsString("Unexpected error")));
    }


    @Test
    @DisplayName("Always calls factory with key 'british'")
    void callsFactoryWithBritishKey() throws Exception {
        when(timeServiceFactory.getService("british")).thenReturn(timeConversionService);
        when(timeConversionService.toSpokenTime(any()))
                .thenReturn(new SpokenTimeResponse("09:00", "nine o' clock"));

        mockMvc.perform(get("/api/time/spoken").param("time", "09:00"))
                .andExpect(status().isOk());

        verify(timeServiceFactory).getService(eq("british"));
    }
    @ParameterizedTest(name = "time={0} -> spoken=\"{1}\"")
    @CsvSource({
            "00:05, five past midnight",
            "01:15, quarter past one",
            "02:30, half past two",
            "03:45, quarter to four",
            "11:59, one to twelve",
            "12:01, one past twelve",
            "18:00, six o' clock"
    })
    void spokenTime_various(String input, String spoken) throws Exception {
        when(timeServiceFactory.getService("british")).thenReturn(timeConversionService);
        when(timeConversionService.toSpokenTime(LocalTime.parse(input)))
                .thenReturn(new SpokenTimeResponse(input, spoken));

        mockMvc.perform(get("/api/time/spoken").param("time", input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.input").value(input))
                .andExpect(jsonPath("$.spokenTime").value(spoken));
    }
}

