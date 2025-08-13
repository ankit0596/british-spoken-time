package com.example.britishtime.exception;

import com.example.britishtime.dto.Error;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;


import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("Should return BAD_REQUEST when MissingServletRequestParameterException is thrown")
    void testHandleMissingRequestParameter() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("time", "String");

        ResponseEntity<Error> response = handler.handleMissingRequestParameter(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("BAD_REQUEST", response.getBody().getError());
        assertEquals("Missing required parameter: time", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should return BAD_REQUEST when IllegalArgumentException is thrown")
    void testHandleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid time format");

        ResponseEntity<Error> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("BAD_REQUEST", response.getBody().getError());
        assertEquals("Invalid time format", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException with null message gracefully")
    void testHandleIllegalArgumentWithNullMessage() {
        IllegalArgumentException ex = new IllegalArgumentException((String) null);

        ResponseEntity<Error> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("BAD_REQUEST", response.getBody().getError());
        assertNull(response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should return INTERNAL_SERVER_ERROR when generic Exception is thrown")
    void testHandleGenericException() {
        Exception ex = new Exception("Unexpected failure");

        ResponseEntity<Error> response = handler.handleGeneric(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_ERROR", response.getBody().getError());
        assertEquals("Unexpected error: Unexpected failure", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should handle generic Exception with null message")
    void testHandleGenericExceptionWithNullMessage() {
        Exception ex = new Exception((String) null);

        ResponseEntity<Error> response = handler.handleGeneric(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("INTERNAL_ERROR", response.getBody().getError());
        assertEquals("Unexpected error: null", response.getBody().getMessage());
    }
}
