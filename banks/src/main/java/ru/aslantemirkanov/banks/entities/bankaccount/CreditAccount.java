package ru.aslantemirkanov.banks.entities.bankaccount;

import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.Client;
import ru.aslantemirkanov.banks.entities.notifications.ChangeType;
import ru.aslantemirkanov.banks.entities.trancations.TransactionLog;
import ru.aslantemirkanov.banks.exceptions.bankexception.CreditLimitExcessException;
import ru.aslantemirkanov.banks.exceptions.bankexception.NonExistChangeTypeException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreditAccount implements BankAccount {
    private double balance;
    private double interestRate;
    private double creditLimit;
    private double lendMoney;
    private final UUID accountId;
    private LocalDate closingDate;
    private final Client client;
    private final AccountType accountType;
    private final Bank bank;
    private List<TransactionLog> transactions;

    public CreditAccount(
            Bank bank,
            Client client,
            double interestRate,
            double creditLimit,
            double balance,
            AccountType accountType) {
        this.bank = bank;
        this.client = client;
        this.balance = balance;
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
        this.accountId = UUID.randomUUID();
        this.closingDate = LocalDate.now().plusMonths(3);
        this.accountType = accountType;
        this.lendMoney = 0;
        this.transactions = new ArrayList<TransactionLog>();
    }


    @Override
    public void fillUpMoney(double moneyAmount) {
        if (lendMoney == 0) {
            balance += moneyAmount;
        } else {
            if (moneyAmount >= lendMoney) {
                balance = moneyAmount - lendMoney;
                lendMoney = 0;
            } else {
                balance = 0;
                lendMoney -= moneyAmount;
            }
        }
    }

    @Override
    public void takeOffMoney(double moneyAmount) {
        if (balance - moneyAmount >= 0) {
            balance -= moneyAmount;
        } else {
            if (Math.abs(balance - moneyAmount) + lendMoney + interestRate > creditLimit) {
                throw new CreditLimitExcessException();
            }
            lendMoney += Math.abs(balance - moneyAmount) + interestRate;
            balance = 0;
        }
    }

    @Override
    public UUID getAccountId() {
        return accountId;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    public void changeCreditLimit(double newCreditLimit) {
        creditLimit = newCreditLimit;
    }

    public void changeCreditInterest(double newCreditInterest) {
        interestRate = newCreditInterest;
    }

    @Override
    public void changeParameter(ChangeType changeType, double newParameter) {
        switch (changeType) {
            case CreditInterest -> changeCreditInterest(newParameter);
            case CreditLimit -> changeCreditLimit(newParameter);
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