package com.example.britishtime.testUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/test")
public class TestExceptionController {
    @GetMapping("/datetime")
    public void triggerDateTimeParseException(@RequestParam String time) {
        throw new DateTimeParseException("Invalid format", time, 0);
    }

    @GetMapping("/general")
    public void triggerGeneralException(@RequestParam String time) {
        throw new RuntimeException("Something went wrong");
    }
}
