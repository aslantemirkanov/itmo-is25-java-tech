package ru.aslantemirkanov.banks.entities.trancations;

import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;

public class WithdrawalTransaction {
    public void transactionExecute(BankAccount bankAccount, double moneyAmount)
    {
        bankAccount.takeOffMoney(moneyAmount);
    }
}
