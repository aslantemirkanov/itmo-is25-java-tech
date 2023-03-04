package ru.aslantemirkanov.banks.exceptions.bankexception;

import ru.aslantemirkanov.banks.entities.Bank;

import java.util.UUID;

public class WrongAccountIdException extends BankException {
    public WrongAccountIdException(UUID accountId) {
        super("Account with id " + accountId + " doesn't exist");
    }
}