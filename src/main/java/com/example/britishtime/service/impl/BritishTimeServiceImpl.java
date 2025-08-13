package com.example.britishtime.service.impl;

import com.example.britishtime.dto.SpokenTimeResponseDto;
import com.example.britishtime.factory.TimePhraseFactory;
import com.example.britishtime.service.BritishTimeService;
import com.example.britishtime.strategy.TimePhraseStrategy;
import org.springframework.stereotype.Service;
import java.time.LocalTime;


@Service
public class BritishTimeServiceImpl implements BritishTimeService{

    @Override
    public SpokenTimeResponseDto toSpokenTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        TimePhraseStrategy strategy = TimePhraseFactory.getStrategy(hour, minute);
        return strategy.toWords(hour, minute);
    }
}
