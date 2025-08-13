package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.util.NumberToWordsConverter;
import com.example.britishtime.util.TimePhraseBuilder;
import com.example.britishtime.util.TimeUtils;

public class HalfPastStrategy implements TimePhraseStrategy {
    @Override
    public SpokenTimeResponse toWords(int hour, int minute) {
        String spoken = new TimePhraseBuilder()
                .withMinute("half")
                .past()
                .hour(NumberToWordsConverter.getInstance().wordForHour(hour))
                .build();
        return new SpokenTimeResponse(TimeUtils.formatTime(hour, minute), spoken);
    }
}
