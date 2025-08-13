package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ToStrategyTest {

    private final ToStrategy strategy = new ToStrategy();

    @ParameterizedTest(name = "{0}:{1} → \"{2}\"")
    @CsvSource({
            // minutesTo = 60 - minute
            "4, 50, ten to five",
            "7, 45, quarter to eight",
            "11, 59, one to twelve",
            "12, 50, ten to one"
    })
    @DisplayName("Should return correct 'to' spoken time phrases")
    void testToPhrases(int hour, int minute, String expectedPhrase) {
        SpokenTimeResponse response = strategy.toWords(hour, minute);

        assertNotNull(response, "Response should not be null");
        assertEquals(TimeUtils.formatTime(hour, minute), response.getInput(), "Formatted time mismatch");
        assertEquals(expectedPhrase, response.getSpokenTime(), "Spoken phrase mismatch");
    }
}
