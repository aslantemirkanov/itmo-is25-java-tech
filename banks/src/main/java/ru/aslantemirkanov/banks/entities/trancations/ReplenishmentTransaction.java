package ru.aslantemirkanov.banks.entities.trancations;

import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;

public class ReplenishmentTransaction {
    public void transactionExecute(BankAccount bankAccount, double moneyAmount)
    {
        bankAccount.fillUpMoney(moneyAmount);
    }
}
