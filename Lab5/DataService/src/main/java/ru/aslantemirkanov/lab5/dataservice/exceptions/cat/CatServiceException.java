package ru.aslantemirkanov.lab5.dataservice.exceptions.cat;

public class CatServiceException extends RuntimeException {
    public CatServiceException(String message) {
        super(message);
    }
}

