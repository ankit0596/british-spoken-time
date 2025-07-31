package com.example.britishtime.api;

import com.example.britishtime.dto.SpokenTimeResponseDto;
import com.example.britishtime.service.BritishTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/time")
public class BritishSpokenTimeController {

    private final BritishTimeService service;


    public BritishSpokenTimeController(BritishTimeService service) {
        this.service = service;
    }

    @GetMapping("/spoken")
    public ResponseEntity<SpokenTimeResponseDto> getSpokenTime(@RequestParam String time) {
        LocalTime localTime = LocalTime.parse(time);
        return ResponseEntity.ok(service.toSpokenTime(localTime));
    }
}
