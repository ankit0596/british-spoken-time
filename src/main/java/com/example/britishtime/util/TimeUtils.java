package com.example.britishtime.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {
    public static LocalTime parseTimeOrThrow(String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:mm (e.g., 10:30)");
        }
    }

    public static String formatTime(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }
}
