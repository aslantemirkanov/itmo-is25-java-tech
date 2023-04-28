package ru.aslantemirkanov.lab3.application.exception.cat;

public class NoneExistCatException extends CatServiceException {
    public NoneExistCatException(long id){
        super("Cat with id = " + id + " doesn't exist");
    }
}
