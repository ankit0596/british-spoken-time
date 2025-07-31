package com.example.britishtime.service.impl;

import com.example.britishtime.dto.SpokenTimeResponseDto;
import com.example.britishtime.service.BritishTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BritishTimeServiceImplTest {

    private BritishTimeService service;

    @BeforeEach
    void setUp() {
        service = new BritishTimeServiceImpl();
    }

    @Test
    @DisplayName("Should return 'midnight' for 00:00")
    void testMidnight() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(0, 0));
        assertEquals("midnight", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'noon' for 12:00")
    void testNoon() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(12, 0));
        assertEquals("noon", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'two o'clock' for 02:00")
    void testExactHour() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(2, 0));
        assertEquals("two o'clock", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'quarter past two' for 02:15")
    void testQuarterPast() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(2, 15));
        assertEquals("quarter past two", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'half past two' for 02:30")
    void testHalfPast() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(2, 30));
        assertEquals("half past two", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'quarter to three' for 02:45")
    void testQuarterTo() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(2, 45));
        assertEquals("quarter to three", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'twenty two past two' for 02:22")
    void testRandomPastTime() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(2, 22));
        assertEquals("twenty-two past two", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should return 'twenty two to three' for 02:38")
    void testRandomToTime() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(2, 38));
        assertEquals("twenty-two to three", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should correctly wrap hour around 12 to 1")
    void testWrapAroundToNextHour() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(12, 45));
        assertEquals("quarter to one", response.getSpokenTime());
    }

    @Test
    @DisplayName("Should correctly convert 11:59 to 'one to twelve'")
    void testOneToTwelve() {
        SpokenTimeResponseDto response = service.toSpokenTime(LocalTime.of(11, 59));
        assertEquals("one to twelve", response.getSpokenTime());
    }
}
