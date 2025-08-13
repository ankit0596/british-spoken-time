package com.example.britishtime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SpokenTimeResponse {
    private String input;
    private String spokenTime;
}
