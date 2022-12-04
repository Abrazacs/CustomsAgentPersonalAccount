package ru.ssemenov.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleMailException(MailException e) {
        return new ResponseEntity<>(new AppError("MAIL_EXCEPTION", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
