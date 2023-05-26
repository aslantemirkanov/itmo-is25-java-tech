package ru.aslantemirkanov.lab5.dataservice.exceptions.owner;

public class NoneExistCatOwnerException extends CatOwnerServiceException {
    public NoneExistCatOwnerException(long id) {
        super("CatOwner with id = " + id + " doesn't exist");
    }
}
