package ru.aslantemirkanov.banks.entities.trancations;

import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;

public class TransferTransaction {
    public void transactionExecute(BankAccount accountFrom, BankAccount accountTo, double moneyAmount)
    {
        accountFrom.takeOffMoney(moneyAmount);
        accountTo.fillUpMoney(moneyAmount);
    }
}
