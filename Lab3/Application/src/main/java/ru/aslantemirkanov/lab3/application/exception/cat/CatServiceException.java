package ru.aslantemirkanov.lab3.application.exception.cat;

public class CatServiceException extends RuntimeException {
    public CatServiceException(String message) {
        super(message);
    }
}

