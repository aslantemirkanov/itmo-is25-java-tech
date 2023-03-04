package ru.aslantemirkanov.banks.exceptions.bankexception;

public class WithdrawingFromDepositAccountException extends BankException{
    public WithdrawingFromDepositAccountException() {
        super("You cant withdraw money from deposit account while it's exist");
    }
}
