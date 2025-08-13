package com.example.britishtime.api;

import com.example.britishtime.model.LocaleType;
import com.example.britishtime.dto.SpokenTimeResponseDto;
import com.example.britishtime.factory.TimeServiceFactory;
import com.example.britishtime.service.TimeConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/time")
public class BritishSpokenTimeController {

    private final TimeServiceFactory serviceFactory;

    public BritishSpokenTimeController(TimeServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @GetMapping("/spoken")
    public ResponseEntity<SpokenTimeResponseDto> getSpokenTime(@RequestParam String time) {
        LocalTime localTime = parseTimeOrThrow(time);
        // In the future, if we wish to covert spokenTime for a different region, we can take locale as input from the user and accordingly get the service by passing it to the factory
        // currently hardcoding to british
        LocaleType localeType = LocaleType.fromString("british");
        TimeConversionService service = serviceFactory.getService(localeType.getKey());
        return ResponseEntity.ok(service.toSpokenTime(localTime));
    }

    private LocalTime parseTimeOrThrow(String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:mm (e.g., 10:30)");
        }
    }
}
