package ru.aslantemirkanov.lab3.application.exception.owner;

public class NoneExistCatOwnerException extends CatOwnerServiceException {
    public NoneExistCatOwnerException(long id) {
        super("CatOwner with id = " + id + " doesn't exist");
    }
}
