package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.NumberToWordsConverter;
import com.example.britishtime.util.TimePhraseBuilder;
import com.example.britishtime.util.TimeUtils;

public class PastStrategy implements TimePhraseStrategy {
    @Override
    public SpokenTimeResponse toWords(int hour, int minute) {
        NumberToWordsConverter converter = NumberToWordsConverter.getInstance();
        String spoken = new TimePhraseBuilder()
                .withMinute(converter.wordForMinute(minute))
                .past()
                .hour(converter.wordForHour(hour))
                .build();
        return new SpokenTimeResponse(TimeUtils.formatTime(hour, minute), spoken);
    }
}

