package com.example.britishtime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SpokenTimeResponseDto {
    private String input;
    private String spokenTime;
}
