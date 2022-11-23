package ru.ssemenov.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>(new AppError("BAD_CREDENTIALS", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}