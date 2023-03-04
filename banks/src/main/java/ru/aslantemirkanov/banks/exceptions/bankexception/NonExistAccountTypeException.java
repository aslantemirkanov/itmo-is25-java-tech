package ru.aslantemirkanov.banks.exceptions.bankexception;

public class NonExistAccountTypeException extends BankException {
    public NonExistAccountTypeException() {
        super("That account type doesn't exist");
    }
}
