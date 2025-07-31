package com.example.britishtime.handler;


import com.example.britishtime.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import java.time.format.DateTimeParseException;

import static com.example.britishtime.ApplicationConstants.INVALID_TIME_FORMAT;
import static com.example.britishtime.ApplicationConstants.INVALID_TIME_FORMAT_USE_HH_MM;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Error> handleDateTimeParseException(DateTimeParseException ex, WebRequest request) {
        String inputTime = request.getParameter("time");
        com.example.britishtime.dto.Error error = new Error(
                inputTime,
                INVALID_TIME_FORMAT,
                INVALID_TIME_FORMAT_USE_HH_MM
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGeneralException(Exception ex, WebRequest request) {
        Error error = new Error(
                request.getParameter("time"),
                ex.getMessage(),
                "Bad Request"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
