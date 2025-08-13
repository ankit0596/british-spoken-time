package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.NumberToWordsConverter;
import com.example.britishtime.util.TimeUtils;

public class ExactHourStrategy implements TimePhraseStrategy {
    @Override
    public SpokenTimeResponse toWords(int hour, int minute) {
        String spoken = NumberToWordsConverter.getInstance().wordForHour(hour) + " o'clock";
        return new SpokenTimeResponse(TimeUtils.formatTime(hour, minute), spoken);
    }
}
