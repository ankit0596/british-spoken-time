package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.TimeUtils;

public class SpecialCaseStrategy implements TimePhraseStrategy {
    @Override
    public SpokenTimeResponse toWords(int hour, int minute) {
        String spoken;
        if (hour == 0 && minute == 0) spoken = "midnight";
        else if (hour == 12 && minute == 0) spoken = "noon";
        else spoken = "";
        return new SpokenTimeResponse(TimeUtils.formatTime(hour, minute), spoken);
    }
}
