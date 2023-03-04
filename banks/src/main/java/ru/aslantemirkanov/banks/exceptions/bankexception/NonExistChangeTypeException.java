package ru.aslantemirkanov.banks.exceptions.bankexception;

public class NonExistChangeTypeException extends BankException {
    public NonExistChangeTypeException() {
        super("You want to change something strange");
    }
}
