package com.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advice of AOP for exception handlers
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("Argument not valid", ex);

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(status)
                .timestamp(new Date().getTime())
                .message("Argument not valid")
                .errors(errors)
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        LOGGER.error("Resource not found", ex);

        HttpStatus status = HttpStatus.NOT_FOUND;
        List<String> errors = Stream.of(ex.getMessage()).collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(status)
                .timestamp(new Date().getTime())
                .message("Resource Not Found")
                .errors(errors)
                .build();

        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.error("Argument not valid", ex);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = Stream.of(ex.getMessage()).collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(status)
                .timestamp(new Date().getTime())
                .message("Constraint Violation")
                .errors(errors)
                .build();

        return new ResponseEntity<>(apiError, status);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ApiError {
        private HttpStatus status;
        private long timestamp;
        private String message;
        private List<String> errors;
    }
}
