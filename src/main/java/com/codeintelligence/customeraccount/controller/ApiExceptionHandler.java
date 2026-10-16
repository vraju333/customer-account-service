package com.codeintelligence.customeraccount.controller;

import com.codeintelligence.customeraccount.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(CustomerNotFoundException ex) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage())); }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> badRequest(IllegalArgumentException ex) { return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage())); }
}
