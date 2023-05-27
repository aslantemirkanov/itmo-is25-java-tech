package ru.aslantemirkanov.lab3.application.exception.user;

public class ExtraOwnerException extends UserException{
    public ExtraOwnerException(String message) {
        super("User " + message + " is already has CatOwner");
    }
}
