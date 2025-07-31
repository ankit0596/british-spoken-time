package com.example.britishtime.handler;


import com.example.britishtime.testUtil.TestExceptionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TestExceptionController.class)
@Import(com.example.britishtime.handler.GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDateTimeParseExceptionHandling() throws Exception {
        mockMvc.perform(get("/test/datetime?time=badTime"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.input").value("badTime"))
                .andExpect(jsonPath("$.message").value("Invalid time format. Please use HH:mm."));
    }

    @Test
    void testGeneralExceptionHandling() throws Exception {
        mockMvc.perform(get("/test/general?time=test"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.input").value("test"))
                .andExpect(jsonPath("$.message").value("Bad Request"));
    }
}
