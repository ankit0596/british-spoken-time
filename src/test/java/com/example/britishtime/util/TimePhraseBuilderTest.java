package com.example.britishtime.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimePhraseBuilderTest {

    @Nested
    @DisplayName("Method chaining behavior")
    class ChainingTests {

        @Test
        @DisplayName("Should build phrase for minute past hour")
        void testPastPhrase() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("ten")
                    .past()
                    .hour("five")
                    .build();

            assertEquals("ten past five", phrase);
        }

        @Test
        @DisplayName("Should build phrase for minute to hour")
        void testToPhrase() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("quarter")
                    .to()
                    .hour("six")
                    .build();

            assertEquals("quarter to six", phrase);
        }

        @Test
        @DisplayName("Should allow only hour without minute or direction")
        void testOnlyHour() {
            String phrase = new TimePhraseBuilder()
                    .hour("midnight")
                    .build();

            assertEquals("midnight", phrase);
        }

        @Test
        @DisplayName("Should allow only minute without hour")
        void testOnlyMinute() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("five")
                    .build();

            assertEquals("five", phrase);
        }

        @Test
        @DisplayName("Should allow minute + past without hour")
        void testMinuteAndPastOnly() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("ten")
                    .past()
                    .build();

            assertEquals("ten past", phrase);
        }
    }

    @Nested
    @DisplayName("Edge and formatting cases")
    class EdgeCases {

        @Test
        @DisplayName("Should trim trailing spaces in final phrase")
        void testTrimSpaces() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("ten")
                    .past()
                    .hour("five ")
                    .build();

            assertEquals("ten past five", phrase);
        }

        @Test
        @DisplayName("Should handle multiple calls to methods in sequence")
        void testMultipleCalls() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("five")
                    .withMinute("minutes")
                    .past()
                    .past() // duplicate
                    .hour("five")
                    .build();

            assertEquals("five minutes past past five", phrase);
        }

        @Test
        @DisplayName("Should build empty string if no methods called")
        void testEmptyBuild() {
            String phrase = new TimePhraseBuilder().build();
            assertEquals("", phrase);
        }
    }

    @Nested
    @DisplayName("Order of words")
    class OrderTests {

        @Test
        @DisplayName("Minute before past/to and hour")
        void testMinuteThenPastThenHour() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("twenty")
                    .past()
                    .hour("three")
                    .build();

            assertEquals("twenty past three", phrase);
        }

        @Test
        @DisplayName("Minute before to and hour")
        void testMinuteThenToThenHour() {
            String phrase = new TimePhraseBuilder()
                    .withMinute("ten")
                    .to()
                    .hour("nine")
                    .build();

            assertEquals("ten to nine", phrase);
        }

        @Test
        @DisplayName("Past without minute should still allow hour")
        void testPastThenHour() {
            String phrase = new TimePhraseBuilder()
                    .past()
                    .hour("five")
                    .build();

            assertEquals("past five", phrase);
        }
    }
}
