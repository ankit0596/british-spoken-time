package com.example.britishtime.util;

import java.util.HashMap;
import java.util.Map;

public class NumberToWordsConverter {
    private static volatile NumberToWordsConverter instance;
    private final Map<Integer, String> numberMap = new HashMap<>();

    private NumberToWordsConverter() {
        String[] words = {
                "twelve", "one", "two", "three", "four", "five", "six", "seven",
                "eight", "nine", "ten", "eleven", "twelve", "one"
        };
        for (int i = 0; i < words.length; i++) {
            numberMap.put(i, words[i]);
        }
    }

    public static NumberToWordsConverter getInstance() {
        if (instance == null) { // First check without locking
            synchronized (NumberToWordsConverter.class) {
                if (instance == null) { // Second check with lock
                    instance = new NumberToWordsConverter();
                }
            }
        }
        return instance;
    }

    public String wordForHour(int hour) {
        return numberMap.get(hour % 12);
    }

    public String wordForMinute(int minute) {
        String[] minuteWords = {
                "o'clock", "one", "two", "three", "four", "five", "six", "seven",
                "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
                "quarter", "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
                "twenty-one", "twenty-two", "twenty-three", "twenty-four", "twenty-five",
                "twenty-six", "twenty-seven", "twenty-eight", "twenty-nine", "half"
        };
        return minuteWords[minute];
    }
}
