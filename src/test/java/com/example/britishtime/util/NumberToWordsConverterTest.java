package com.example.britishtime.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberToWordsConverterTest {

    @Test
    @DisplayName("Convert zero")
    void testZero() {
        assertEquals("zero", NumberToWordsConverter.convert(0));
    }

    @Test
    @DisplayName("Convert numbers below 20")
    void testBelowTwenty() {
        assertEquals("one", NumberToWordsConverter.convert(1));
        assertEquals("eleven", NumberToWordsConverter.convert(11));
        assertEquals("nineteen", NumberToWordsConverter.convert(19));
    }

    @Test
    @DisplayName("Convert multiples of ten")
    void testMultiplesOfTen() {
        assertEquals("twenty", NumberToWordsConverter.convert(20));
        assertEquals("fifty", NumberToWordsConverter.convert(50));
    }

    @Test
    @DisplayName("Convert numbers between 21–59")
    void testCompoundNumbers() {
        assertEquals("twenty-one", NumberToWordsConverter.convert(21));
        assertEquals("thirty-five", NumberToWordsConverter.convert(35));
        assertEquals("forty-nine", NumberToWordsConverter.convert(49));
    }

    @Test
    @DisplayName("Convert upper bound number")
    void testUpperLimit() {
        assertEquals("fifty-nine", NumberToWordsConverter.convert(59));
    }
}
