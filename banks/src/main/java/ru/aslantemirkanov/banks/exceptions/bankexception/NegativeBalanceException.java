package ru.aslantemirkanov.banks.exceptions.bankexception;

public class NegativeBalanceException extends BankException {
    public NegativeBalanceException() {
        super("Not enough money in the account");
    }
}