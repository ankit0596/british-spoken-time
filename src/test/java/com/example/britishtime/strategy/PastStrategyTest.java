package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PastStrategyTest {

    private final PastStrategy strategy = new PastStrategy();

    @ParameterizedTest(name = "{0}:{1} → \"{2}\"")
    @CsvSource({
            // Common past times
            "1, 5, five past one",
            "2, 10, ten past two",
            "3, 15, quarter past three",
            "4, 20, twenty past four",
            "6, 25, twenty-five past six",
            "7, 1, one past seven",
            "8, 29, twenty-nine past eight"
    })
    @DisplayName("Should return correct spoken phrase for past-the-hour times")
    void testValidPastTimes(int hour, int minute, String expectedPhrase) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        assertNotNull(response, "Response should not be null");
        assertEquals(TimeUtils.formatTime(hour, minute), response.getInput(), "Formatted time mismatch");
        assertEquals(expectedPhrase, response.getSpokenTime(), "Spoken phrase mismatch");
    }

}
