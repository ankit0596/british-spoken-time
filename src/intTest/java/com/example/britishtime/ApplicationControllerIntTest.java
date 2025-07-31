package com.example.britishtime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpokenTimeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnQuarterPastTwo() throws Exception {
        mockMvc.perform(get("/api/time/spoken")
                        .param("time", "02:15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.input").value("02:15"))
                .andExpect(jsonPath("$.spokenTime").value("quarter past two"));
    }

    @Test
    void shouldReturnMidnight() throws Exception {
        mockMvc.perform(get("/api/time/spoken")
                        .param("time", "00:00")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.spokenTime").value("midnight"));
    }

    @Test
    void shouldReturnBadRequestForInvalidTime() throws Exception {
        mockMvc.perform(get("/api/time/spoken")
                        .param("time", "notatime")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INVALID TIME FORMAT"));
    }
}
