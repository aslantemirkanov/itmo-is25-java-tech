package ru.aslantemirkanov.banks.exceptions.bankexception;

public class WrongDepositBalanceException extends BankException {
    public WrongDepositBalanceException(double balance) {
        super(String.format(
                "You can't create deposit account with balance %f, because bank haven't that rate", balance));
    }
}
