package ru.aslantemirkanov.banks.exceptions.bankexception;

public class TryToCreateVerificatedAccountException extends BankException {
    public TryToCreateVerificatedAccountException() {
        super("You can't create credit account for unveficated client ");
    }
}
