package ru.aslantemirkanov.lab5.dataservice.exceptions.user;

public class ExtraOwnerException extends UserException{
    public ExtraOwnerException(String message) {
        super("User " + message + " is already has CatOwner");
    }
}
