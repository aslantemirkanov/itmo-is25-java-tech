package ru.aslantemirkanov.lab3.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.aslantemirkanov.lab3.application.exception.cat.CatServiceException;
import ru.aslantemirkanov.lab3.application.exception.owner.CatOwnerServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CatServiceException.class)
    public ResponseEntity<String> handleCatException(CatServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }

    @ExceptionHandler(CatOwnerServiceException.class)
    public ResponseEntity<String> handleCatOwnerException(CatOwnerServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
}
