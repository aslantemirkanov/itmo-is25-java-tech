package ru.aslantemirkanov.banks.exceptions.bankexception;

public class ExtraWithdrawalLimitException extends BankException{
    public ExtraWithdrawalLimitException(double limit) {
        super(String.format("You can't take off more than limit %f", limit));
    }
}
