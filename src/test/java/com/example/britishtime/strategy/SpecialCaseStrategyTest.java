package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpecialCaseStrategyTest {

    private final SpecialCaseStrategy strategy = new SpecialCaseStrategy();

    @ParameterizedTest(name = "{0}:{1} → \"{2}\"")
    @CsvSource({
            "0, 0, midnight",
            "12, 0, noon"
    })
    @DisplayName("Should return correct special spoken time for midnight and noon")
    void testSpecialCases(int hour, int minute, String expectedPhrase) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        assertNotNull(response, "Response should not be null");
        assertEquals(TimeUtils.formatTime(hour, minute), response.getInput(), "Formatted time mismatch");
        assertEquals(expectedPhrase, response.getSpokenTime(), "Spoken phrase mismatch");
    }

    @ParameterizedTest(name = "{0}:{1} → empty string")
    @CsvSource({
            "1, 0",
            "3, 15",
            "5, 45"
    })
    @DisplayName("Should return empty spoken phrase for non-special times")
    void testNonSpecialCases(int hour, int minute) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        assertNotNull(response, "Response should not be null");
        assertEquals("", response.getSpokenTime(), "Expected empty spoken phrase for non-special time");
    }
}
