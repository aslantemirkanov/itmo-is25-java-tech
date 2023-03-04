package ru.aslantemirkanov.banks.entities.bankaccount;

import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.Client;
import ru.aslantemirkanov.banks.entities.notifications.ChangeType;
import ru.aslantemirkanov.banks.entities.trancations.TransactionLog;
import ru.aslantemirkanov.banks.exceptions.bankexception.NegativeBalanceException;
import ru.aslantemirkanov.banks.exceptions.bankexception.NonExistChangeTypeException;
import ru.aslantemirkanov.banks.exceptions.bankexception.WithdrawingFromDepositAccountException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepositAccount implements BankAccount {
    private LocalDate closingDate;
    private double interestRate;
    private UUID accountId;
    private double balance;
    private Client client;
    private AccountType accountType;
    private Bank bank;
    private double interestBank;
    private List<TransactionLog> transactions;

    public DepositAccount(
            Bank bank,
            Client client,
            double interestRate,
            double balance,
            AccountType accountType) {
        this.bank = bank;
        this.client = client;
        this.balance = balance;
        this.accountType = accountType;
        this.closingDate = LocalDate.now().plusMonths(3);
        this.accountId = UUID.randomUUID();
        this.interestBank = 0;
        this.transactions = new ArrayList<TransactionLog>();
    }

    @Override
    public void fillUpMoney(double moneyAmount) {
        balance += moneyAmount;
    }

    @Override
    public void takeOffMoney(double moneyAmount) {
        if (LocalDate.now().isBefore(closingDate)) {
            throw new WithdrawingFromDepositAccountException();
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

    public void addDailyInterest() {
        interestBank += balance * ((interestRate / 100) / 365);
    }

    public void addMonthInterest() {
        balance += interestBank;
        interestBank = 0;
    }

    public void changeDepositInterest(double newDepositInterest) {
        interestRate = newDepositInterest;
    }

    @Override
    public void changeParameter(ChangeType changeType, double newParameter) {
        switch (changeType) {
            case DepositInterest -> changeDepositInterest(newParameter);
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
