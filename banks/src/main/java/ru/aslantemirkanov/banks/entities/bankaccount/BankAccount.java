package ru.aslantemirkanov.banks.entities.bankaccount;

import ru.aslantemirkanov.banks.entities.notifications.ChangeType;
import ru.aslantemirkanov.banks.entities.trancations.TransactionLog;

import java.util.List;
import java.util.UUID;

public interface BankAccount {
    void fillUpMoney(double moneyAmount);
    void takeOffMoney(double moneyAmount);
    UUID getAccountId();
    AccountType getAccountType();
    void changeParameter(ChangeType changeType, double newParameter);
    double getBalance();
    void removeTransactionLog(TransactionLog transactionLog);
    void addTransactionLog(TransactionLog transactionLog);

    List<TransactionLog> getTransactions();
}
