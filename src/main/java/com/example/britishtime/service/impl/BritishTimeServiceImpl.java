package com.example.britishtime.service.impl;

import com.example.britishtime.dto.SpokenTimeResponseDto;
import com.example.britishtime.service.BritishTimeService;
import com.example.britishtime.util.NumberToWordsConverter;
import org.springframework.stereotype.Service;

import java.time.LocalTime;


import static com.example.britishtime.ApplicationConstants.MIDNIGHT;
import static com.example.britishtime.ApplicationConstants.NOON;
import static com.example.britishtime.ApplicationConstants.O_CLOCK;
import static com.example.britishtime.ApplicationConstants.PAST;
import static com.example.britishtime.ApplicationConstants.SPACE;
import static com.example.britishtime.ApplicationConstants.SPECIAL_MINUTES;
import static com.example.britishtime.ApplicationConstants.TIME_TEMPLATE;
import static com.example.britishtime.ApplicationConstants.TO;

@Service
public class BritishTimeServiceImpl implements BritishTimeService {

    @Override
    public SpokenTimeResponseDto toSpokenTime(LocalTime time) {
        return new SpokenTimeResponseDto(time.toString(), getSpokenTime(time));
    }

    private String getSpokenTime(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if (hour == 0 && minute == 0) return MIDNIGHT;
        if (hour == 12 && minute == 0) return NOON;
        if (minute == 0) return NumberToWordsConverter.convert(hour % 12) + SPACE + O_CLOCK;

        boolean isPast = minute <= 30;

        return String.format(TIME_TEMPLATE,
                getMinuteWord(isPast, minute),
                isPast ? PAST : TO,
                getHourWord(isPast, hour));

    }

    private String getMinuteWord(boolean isPast, int minute) {
        int effectiveMinute = isPast ? minute : 60 - minute;

        return SPECIAL_MINUTES.getOrDefault(minute,
                NumberToWordsConverter.convert(effectiveMinute));
    }

    private String getHourWord(boolean isPast, int hour){
        int adjustedHour = isPast ? hour : hour + 1;
        adjustedHour = get12HourFormat(adjustedHour);
        return NumberToWordsConverter.convert(adjustedHour);
    }

    private int get12HourFormat(int hour) {
        int formatted = hour % 12;
        return formatted == 0 ? 12 : formatted;
    }
}
