package com.example.britishtime.api;

import com.example.britishtime.dto.SpokenTimeResponseDto;
import com.example.britishtime.service.BritishTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BritishSpokenTimeControllerTest {

    private BritishSpokenTimeController controller;
    private BritishTimeService service;

    @BeforeEach
    void setup() {
        service = mock(BritishTimeService.class);
        controller = new BritishSpokenTimeController(service);
    }

    @Test
    @DisplayName("Should return spoken time for valid input")
    void testValidTime() {
        String input = "02:15";
        String expectedSpoken = "quarter past two";

        LocalTime parsedTime = LocalTime.parse(input);
        SpokenTimeResponseDto expectedDto = new SpokenTimeResponseDto(input, expectedSpoken);

        when(service.toSpokenTime(parsedTime)).thenReturn(expectedDto);

        ResponseEntity<SpokenTimeResponseDto> response = controller.getSpokenTime(input);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(input, response.getBody().getInput());
        assertEquals(expectedSpoken, response.getBody().getSpokenTime());
    }

    @Test
    @DisplayName("Should throw exception for invalid time")
    void testInvalidTimeThrowsException() {
        String invalidInput = "invalid";

        assertThrows(Exception.class, () -> controller.getSpokenTime(invalidInput));
    }
}
