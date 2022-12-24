package ru.ssemenov.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ssemenov.dtos.Violation;

import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<AppError> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.error("Error validation, response={}", violations);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.name(), e.getMessage(), violations), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>(new AppError("BAD_CREDENTIALS", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleRegistrationException(RegistrationException e){
        return new ResponseEntity<>(new AppError("CHECK_USERNAME", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.name(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppError> onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        log.error("Error validation, violations={}", violations);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.name(), e.getMessage(), violations), HttpStatus.BAD_REQUEST);
    }
}