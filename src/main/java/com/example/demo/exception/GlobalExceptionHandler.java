package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles Resource Not Found & Email duplicate
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        // Use 404 to avoid 500
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Safety net – NEVER allow 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(
            Exception ex) {

        Map<String, String> response = new HashMap<>();
        response.put("error", "Bad request");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
