package com.example.britishtime.strategy;

import com.example.britishtime.dto.SpokenTimeResponse;

public interface TimePhraseStrategy {
    SpokenTimeResponse toWords(int hour, int minute);
}
