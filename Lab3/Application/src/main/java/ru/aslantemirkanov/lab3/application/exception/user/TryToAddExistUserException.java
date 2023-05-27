package ru.aslantemirkanov.lab3.application.exception.user;

public class TryToAddExistUserException extends UserException {
    public TryToAddExistUserException(String message) {
        super("User " + message + " is already exist");
    }
}
