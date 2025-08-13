package com.example.britishtime.service;

import com.example.britishtime.dto.SpokenTimeResponse;

import java.time.LocalTime;

public interface TimeConversionService {
    SpokenTimeResponse toSpokenTime(LocalTime time);
}
