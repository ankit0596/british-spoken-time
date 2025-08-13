package com.example.britishtime.api;

import com.example.britishtime.model.LocaleType;
import com.example.britishtime.dto.SpokenTimeResponse;
import com.example.britishtime.factory.TimeServiceFactory;
import com.example.britishtime.service.TimeConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;


import static com.example.britishtime.util.TimeUtils.parseTimeOrThrow;

@RestController
@RequestMapping("/api/time")
public class BritishSpokenTimeController {

    private final TimeServiceFactory serviceFactory;

    public BritishSpokenTimeController(TimeServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @GetMapping("/spoken")
    public ResponseEntity<SpokenTimeResponse> getSpokenTime(@RequestParam String time) {
        LocalTime localTime = parseTimeOrThrow(time);

        // Currently, the spoken time is always generated in British format.
        // In the future, we can enhance this endpoint to accept a locale parameter from the user.
        // That locale can then be passed to the TimeServiceFactory to get the appropriate conversion service.
        LocaleType localeType = LocaleType.fromString("british");
        TimeConversionService service = serviceFactory.getService(localeType.getKey());
        return ResponseEntity.ok(service.toSpokenTime(localTime));
    }


}
