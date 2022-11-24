package ru.ssemenov.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleIllegalStateException(IllegalStateException e) {
        return new ResponseEntity<>(new AppError("ILLEGAL_DATA_STATE", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleRuntimeException(ResourceException e) {
        return new ResponseEntity<>(new AppError("RESOURCE_EXCEPTION", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
