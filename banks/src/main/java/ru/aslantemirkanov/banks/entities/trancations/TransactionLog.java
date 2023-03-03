package ru.aslantemirkanov.banks.entities.trancations;

import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;

import java.util.UUID;

public class TransactionLog {
    private final UUID transactionId;
    private final BankAccount accountFrom;
    private final BankAccount accountTo;
    private final double transferAmount;
    private final TransactionType transactionType;

    public TransactionLog(
            BankAccount accountFrom,
            BankAccount accountTo,
            double transferAmount,
            TransactionType transactionType) {

        this.transactionId = UUID.randomUUID();
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.transferAmount = transferAmount;
        this.transactionType = transactionType;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public BankAccount getAccountFrom() {
        return accountFrom;
    }

    public BankAccount getAccountTo() {
        return accountTo;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
