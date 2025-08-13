package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HalfPastStrategyTest {

    private final HalfPastStrategy strategy = new HalfPastStrategy();

    @ParameterizedTest(name = "Hour {0}:{1} → Expected phrase: \"{2}\"")
    @CsvSource({
            "1, 30, half past one",
            "2, 30, half past two",
            "6, 30, half past six",
            "11, 30, half past eleven",
            "12, 30, half past twelve"
    })
    @DisplayName("Should return correct spoken time for half-past in 12-hour format")
    void testHalfPastTimes(int hour, int minute, String expectedPhrase) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        String expectedTime = TimeUtils.formatTime(hour, minute);

        assertNotNull(response, "Response should not be null");
        assertEquals(expectedTime, response.getInput(), "Formatted time mismatch");
        assertEquals(expectedPhrase, response.getSpokenTime(), "Spoken phrase mismatch");
    }

}
