package ru.aslantemirkanov.banks.exceptions.bankexception;

public class NonExistTransactionException extends BankException{
    public NonExistTransactionException() {
        super("That transaction doesn't exist");
    }
}
