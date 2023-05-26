package ru.aslantemirkanov.lab5.dataservice.exceptions.user;

public class TryToAddExistUserException extends UserException {
    public TryToAddExistUserException(String message) {
        super("User " + message + " is already exist");
    }
}
