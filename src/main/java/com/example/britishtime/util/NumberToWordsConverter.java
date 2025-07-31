package com.example.britishtime.util;

public class NumberToWordsConverter {
    private static final String[] belowTwenty = {
            "", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen",
            "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] tens = {
            "", "", "twenty", "thirty", "forty", "fifty"
    };

    public static String convert(int number) {
        if (number == 0) return "zero";
        if (number < 20) return belowTwenty[number];
        int tenVal = number / 10;
        int rest = number % 10;
        return rest == 0 ? tens[tenVal] : tens[tenVal] + "-" + belowTwenty[rest];
    }
}
