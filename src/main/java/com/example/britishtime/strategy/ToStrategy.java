package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.NumberToWordsConverter;
import com.example.britishtime.util.TimePhraseBuilder;
import com.example.britishtime.util.TimeUtils;

public class ToStrategy implements TimePhraseStrategy {
    @Override
    public SpokenTimeResponse toWords(int hour, int minute) {
        NumberToWordsConverter converter = NumberToWordsConverter.getInstance();
        int minutesTo = 60 - minute;
        String spoken = new TimePhraseBuilder()
                .withMinute(converter.wordForMinute(minutesTo))
                .to()
                .hour(converter.wordForHour(hour + 1))
                .build();
        return new SpokenTimeResponse(TimeUtils.formatTime(hour, minute), spoken);
    }
}
