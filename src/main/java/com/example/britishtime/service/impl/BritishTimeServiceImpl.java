package com.example.britishtime.service.impl;

import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.factory.TimePhraseStrategyFactory;
import com.example.britishtime.service.BritishTimeService;
import com.example.britishtime.strategy.TimePhraseStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.time.LocalTime;


@Service("british")
public class BritishTimeServiceImpl implements BritishTimeService{

    private final TimePhraseStrategyFactory strategyFactory;

    public BritishTimeServiceImpl(@Qualifier("britishTimeStrategyFactory") TimePhraseStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public SpokenTimeResponse toSpokenTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        TimePhraseStrategy strategy = strategyFactory.getStrategy(hour, minute);
        return strategy.toWords(hour, minute);
    }
}
