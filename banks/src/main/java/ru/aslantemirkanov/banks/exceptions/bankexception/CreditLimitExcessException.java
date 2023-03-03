package ru.aslantemirkanov.banks.exceptions.bankexception;

public class CreditLimitExcessException extends BankException{
    public CreditLimitExcessException() {
        super("You can't excess credit limit");
    }
}
