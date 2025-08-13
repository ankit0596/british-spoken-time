package com.example.britishtime.factory;

import com.example.britishtime.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BritishTimeStrategyFactoryTest {

    private final BritishTimeStrategyFactory factory = new BritishTimeStrategyFactory();

    @Test
    @DisplayName("Should return SpecialCaseStrategy for 00:00 (midnight)")
    void testMidnightSpecialCase() {
        TimePhraseStrategy strategy = factory.getStrategy(0, 0);
        assertInstanceOf(SpecialCaseStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should return SpecialCaseStrategy for 12:00 (noon)")
    void testNoonSpecialCase() {
        TimePhraseStrategy strategy = factory.getStrategy(12, 0);
        assertInstanceOf(SpecialCaseStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should return ExactHourStrategy for other full hours")
    void testExactHour() {
        TimePhraseStrategy strategy = factory.getStrategy(3, 0);
        assertInstanceOf(ExactHourStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should return HalfPastStrategy for 30 minutes past any hour")
    void testHalfPast() {
        TimePhraseStrategy strategy = factory.getStrategy(7, 30);
        assertInstanceOf(HalfPastStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should return PastStrategy for minutes less than 30 (but not 0)")
    void testPastMinutes() {
        TimePhraseStrategy strategy = factory.getStrategy(4, 15);
        assertInstanceOf(PastStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should return ToStrategy for minutes greater than 30")
    void testToMinutes() {
        TimePhraseStrategy strategy = factory.getStrategy(5, 45);
        assertInstanceOf(ToStrategy.class, strategy);
    }

    @Test
    @DisplayName("Boundary check: minute 29 should be PastStrategy")
    void testBoundaryMinute29() {
        TimePhraseStrategy strategy = factory.getStrategy(8, 29);
        assertInstanceOf(PastStrategy.class, strategy);
    }

    @Test
    @DisplayName("Boundary check: minute 31 should be ToStrategy")
    void testBoundaryMinute31() {
        TimePhraseStrategy strategy = factory.getStrategy(10, 31);
        assertInstanceOf(ToStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should handle multiple hours for the same minute type")
    void testMultipleHoursSameStrategy() {
        assertInstanceOf(PastStrategy.class, factory.getStrategy(1, 15));
        assertInstanceOf(PastStrategy.class, factory.getStrategy(11, 15));
        assertInstanceOf(ToStrategy.class, factory.getStrategy(2, 45));
        assertInstanceOf(ToStrategy.class, factory.getStrategy(9, 45));
    }

    @Test
    @DisplayName("Should never return null for valid hour/minute input")
    void testNeverNull() {
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                assertNotNull(factory.getStrategy(h, m));
            }
        }
    }
}
