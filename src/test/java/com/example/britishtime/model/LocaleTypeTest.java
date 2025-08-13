package com.example.britishtime.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocaleTypeTest {

    @Nested
    @DisplayName("fromString() method")
    class FromStringTests {

        @Test
        @DisplayName("Should return BRITISH when given lowercase 'british'")
        void testValidLowercase() {
            LocaleType result = LocaleType.fromString("british");
            assertEquals(LocaleType.BRITISH, result);
        }

        @Test
        @DisplayName("Should return BRITISH when given uppercase 'BRITISH'")
        void testValidUppercase() {
            LocaleType result = LocaleType.fromString("BRITISH");
            assertEquals(LocaleType.BRITISH, result);
        }

        @Test
        @DisplayName("Should return BRITISH when given mixed case 'BrItIsH'")
        void testValidMixedCase() {
            LocaleType result = LocaleType.fromString("BrItIsH");
            assertEquals(LocaleType.BRITISH, result);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for unsupported locale")
        void testInvalidLocale() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> LocaleType.fromString("french")
            );
            assertEquals("Unsupported locale: french", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for null input")
        void testNullInput() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> LocaleType.fromString(null)
            );
            assertEquals("Unsupported locale: null", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException for empty string input")
        void testEmptyInput() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> LocaleType.fromString("")
            );
            assertEquals("Unsupported locale: ", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("getKey() method")
    class GetKeyTests {

        @Test
        @DisplayName("Should return 'british' for BRITISH enum constant")
        void testGetKeyForBritish() {
            assertEquals("british", LocaleType.BRITISH.getKey());
        }
    }
}
