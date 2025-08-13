package com.example.britishtime.factory;

import com.example.britishtime.strategy.TimePhraseStrategy;

public interface TimePhraseStrategyFactory {
    TimePhraseStrategy getStrategy(int hour, int minute);
}
