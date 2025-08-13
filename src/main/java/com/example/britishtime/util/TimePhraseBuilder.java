package com.example.britishtime.util;

public class TimePhraseBuilder {
    private StringBuilder phrase = new StringBuilder();

    public TimePhraseBuilder withMinute(String minuteWord) {
        phrase.append(minuteWord).append(" ");
        return this;
    }

    public TimePhraseBuilder past() {
        phrase.append("past ");
        return this;
    }

    public TimePhraseBuilder to() {
        phrase.append("to ");
        return this;
    }

    public TimePhraseBuilder hour(String hourWord) {
        phrase.append(hourWord);
        return this;
    }

    public String build() {
        return phrase.toString().trim();
    }
}