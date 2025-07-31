package com.example.britishtime.dto;

public class SpokenTimeResponseDto {
    private String input;
    private String spokenTime;

    public SpokenTimeResponseDto(String input, String spoken) {
        this.input = input;
        this.spokenTime = spoken;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getSpokenTime() {
        return spokenTime;
    }

    public void setSpokenTime(String spokenTime) {
        this.spokenTime = spokenTime;
    }


}
