package ru.aslantemirkanov.banks.entities.bankaccount;

import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.Client;
import ru.aslantemirkanov.banks.entities.notifications.ChangeType;
import ru.aslantemirkanov.banks.entities.trancations.TransactionLog;
import ru.aslantemirkanov.banks.exceptions.bankexception.ExtraWithdrawalLimitException;
import ru.aslantemirkanov.banks.exceptions.bankexception.NegativeBalanceException;
import ru.aslantemirkanov.banks.exceptions.bankexception.NonExistChangeTypeException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DebitAccount implements BankAccount {
    private LocalDate closingDate;
    private double interestRate;
    private UUID accountId;
    private double balance;
    private double limit;
    private Client client;
    private AccountType accountType;
    private Bank bank;
    private double interestBank;
    private List<TransactionLog> transactions;

    public DebitAccount(
            Bank bank,
            Client client,
            double balance,
            double interestRate,
            double limit,
            AccountType accountType) {
        this.bank = bank;
        this.client = client;
        this.interestRate = interestRate;
        this.balance = balance;
        this.accountId = UUID.randomUUID();
        this.limit = limit;
        this.closingDate = LocalDate.now().plusMonths(3);
        this.accountType = accountType;
        this.interestBank = 0;
        this.transactions = new ArrayList<TransactionLog>();
    }

    @Override
    public void fillUpMoney(double moneyAmount) {
        balance += moneyAmount;
    }

    @Override
    public void takeOffMoney(double moneyAmount) {
        if (Double.POSITIVE_INFINITY != limit) {
            if (moneyAmount > limit) {
                throw new ExtraWithdrawalLimitException(limit);
            }
        }

        if (balance < moneyAmount) {
            throw new NegativeBalanceException();
        }

        balance -= moneyAmount;
    }

    @Override
    public UUID getAccountId() {
        return accountId;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }


    public void changeDebitInterest(double newDebitInterest) {
        interestRate = newDebitInterest;
    }

    public void changeDebitLimit(double newLimit) {
        limit = newLimit;
    }

    public void addDailyInterest() {
        interestBank += balance * ((interestRate / 100) / 365);
    }

    public void addMonthInterest() {
        balance += interestBank;
        interestBank = 0;
    }

    @Override
    public void changeParameter(ChangeType changeType, double newParameter) {
        switch (changeType) {
            case DebitInterest -> changeDebitInterest(newParameter);
            case CreditLimit -> changeDebitLimit(newParameter);
            case AddMonthInterest -> addMonthInterest();
            case AddDayInterest -> addDailyInterest();
            default -> throw new NonExistChangeTypeException();
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void removeTransactionLog(TransactionLog transactionLog) {
        transactions.remove(transactionLog);
    }

    @Override
    public void addTransactionLog(TransactionLog transactionLog) {
        transactions.add(transactionLog);
    }

    @Override
    public List<TransactionLog> getTransactions() {
        return transactions;
    }
}
