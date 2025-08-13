package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExactHourStrategyTest {

    private final ExactHourStrategy strategy = new ExactHourStrategy();

    @ParameterizedTest(name = "Hour {0}:{1} → Expected phrase: \"{2}\"")
    @CsvSource({
            "1, 0, one o'clock",
            "2, 0, two o'clock",
            "5, 0, five o'clock",
            "11, 0, eleven o'clock"
    })
    @DisplayName("Should return correct spoken time for exact hours in 12-hour format")
    void testExactHours(int hour, int minute, String expectedPhrase) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        String expectedTime = TimeUtils.formatTime(hour, minute);

        assertNotNull(response, "Response should not be null");
        assertEquals(expectedTime, response.getInput(), "Formatted time mismatch");
        assertEquals(expectedPhrase, response.getSpokenTime(), "Spoken phrase mismatch");
    }

    @ParameterizedTest(name = "Hour {0}:{1} → Expected o'clock phrase: \"{2}\"")
    @CsvSource({
            "3, 15, three o'clock",
            "7, 45, seven o'clock"
    })
    @DisplayName("Should still append o'clock even if minute != 0 (if strategy is misused)")
    void testNonZeroMinutes(int hour, int minute, String expectedPhrase) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        assertNotNull(response);
        assertEquals(expectedPhrase, response.getSpokenTime());
    }
}
