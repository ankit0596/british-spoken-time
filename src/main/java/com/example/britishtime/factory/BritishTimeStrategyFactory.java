package com.example.britishtime.factory;

import com.example.britishtime.strategy.ExactHourStrategy;
import com.example.britishtime.strategy.HalfPastStrategy;
import com.example.britishtime.strategy.PastStrategy;
import com.example.britishtime.strategy.SpecialCaseStrategy;
import com.example.britishtime.strategy.TimePhraseStrategy;
import com.example.britishtime.strategy.ToStrategy;
import org.springframework.stereotype.Component;

@Component("britishTimeStrategyFactory")
public class BritishTimeStrategyFactory implements TimePhraseStrategyFactory {

    @Override
    public TimePhraseStrategy getStrategy(int hour, int minute) {
        if ((hour == 0 && minute == 0) || (hour == 12 && minute == 0)) return new SpecialCaseStrategy();
        if (minute == 0) return new ExactHourStrategy();
        if (minute == 30) return new HalfPastStrategy();
        if (minute < 30) return new PastStrategy();
        return new ToStrategy();
    }
}
