package ru.aslantemirkanov.lab5.dataservice.exceptions.cat;

public class NoneExistCatException extends CatServiceException {
    public NoneExistCatException(long id){
        super("Cat with id = " + id + " doesn't exist");
    }
}
