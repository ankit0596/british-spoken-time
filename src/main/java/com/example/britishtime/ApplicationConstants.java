package com.example.britishtime;

import java.util.Map;

public final class ApplicationConstants {
    private ApplicationConstants() {}

    public static final String TIME_TEMPLATE = "%s %s %s";
    public static final String MIDNIGHT = "midnight";
    public static final String NOON = "noon";
    public static final String O_CLOCK = "o'clock";
    public static final String PAST = "past";
    public static final String TO = "to";
    public static final String SPACE = " ";
    public static final String QUARTER = "quarter";
    public static final String HALF = "half";
    public static final Map<Integer, String> SPECIAL_MINUTES = Map.of(
            15, QUARTER,
            30, HALF,
            45, QUARTER
    );


}
