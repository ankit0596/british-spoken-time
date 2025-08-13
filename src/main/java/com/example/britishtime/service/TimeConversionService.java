package com.example.britishtime.service;

import com.example.britishtime.dto.SpokenTimeResponseDto;

import java.time.LocalTime;

public interface TimeConversionService {
    SpokenTimeResponseDto toSpokenTime(LocalTime time);
}
