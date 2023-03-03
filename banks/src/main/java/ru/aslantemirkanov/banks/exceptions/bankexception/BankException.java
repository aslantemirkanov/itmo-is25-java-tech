package ru.aslantemirkanov.banks.exceptions.bankexception;

public class BankException extends RuntimeException {
    public BankException(String message) {
        super(message);
    }
}