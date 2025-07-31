package com.example.britishtime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Error {
    private String input;
    private String errorCode;
    private String message;
}
