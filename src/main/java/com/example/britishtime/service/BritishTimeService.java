package com.example.britishtime.service;

import com.example.britishtime.dto.SpokenTimeResponse;

import java.time.LocalTime;

public interface BritishTimeService extends TimeConversionService{
    SpokenTimeResponse toSpokenTime(LocalTime time);
    // This service provides British-specific time conversion logic.
    // It may include additional methods unique to British time formatting conventions,
    // which could differ from other locale-specific implementations.
}
