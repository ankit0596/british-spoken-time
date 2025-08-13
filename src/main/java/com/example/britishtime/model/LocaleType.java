package com.example.britishtime.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum LocaleType {
    BRITISH("british"),
    GERMAN("german"),
    SPANISH("spanish");

    private final String key;

    LocaleType(String key) {
        this.key = key;
    }


    @JsonCreator
    public static LocaleType fromString(String input) {
        for (LocaleType locale : values()) {
            if (locale.key.equalsIgnoreCase(input)) {
                return locale;
            }
        }
        throw new IllegalArgumentException("Unsupported locale: " + input);
    }

}
