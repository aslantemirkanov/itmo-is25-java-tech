package ru.aslantemirkanov.banks.services;

import org.jetbrains.annotations.Nullable;
import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;
import ru.aslantemirkanov.banks.entities.trancations.Transaction;
import ru.aslantemirkanov.banks.entities.trancations.TransactionType;
import ru.aslantemirkanov.banks.exceptions.bankexception.WrongAccountIdException;

import java.time.LocalDate;
import java.util.*;

public class CentralBank
{
    @Nullable
    private static CentralBank instance;
    private List<Bank> banks;
    private LocalDate currentDate;

    private CentralBank() {
        banks = new ArrayList<>();
        currentDate = LocalDate.now();
    }

    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }
    public void transferTransaction(UUID accountFromId, UUID accountToId, double money) {
        BankAccount accountFrom = getBankAccount(accountFromId);
        BankAccount accountTo = getBankAccount(accountToId);
        Transaction.getInstance().executeTransaction(TransactionType.Transfer, money, accountFrom, accountTo);
    }

    public Bank registerBank(String bankName, double debitInterestRate, SortedMap<Double, Double> depositInterestRates,
                             double creditInterestRate, double creditLimit, double suspiciousAccountLimit) {
        Bank bank = new Bank.BankBuilder()
                .addName(bankName)
                .addDebitInterestRate(debitInterestRate)
                .addDepositInterestRate(depositInterestRates)
                .addCreditLimit(creditLimit)
                .addCreditInterestRate(creditInterestRate)
                .addSuspiciousAccountLimit(suspiciousAccountLimit)
                .build();
        banks.add(bank);
        return bank;
    }

    public void registerBank(Bank bank) {
        banks.add(bank);
    }

    public void addMonthInterest() {
        for (Bank bank : banks) {
            bank.addMonthInterest();
        }
    }

    public void addDayInterest() {
        for (Bank bank : banks) {
            bank.addDayInterest();
        }
        currentDate = currentDate.plusDays(1);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public List<Bank> getBanksList() {
        return banks;
    }

    private BankAccount getBankAccount(UUID accountId) {
        BankAccount newBankAccount = null;
        for (Bank bank : banks) {
            BankAccount foundBankAccount = bank.getBankAccount(accountId);
            if (newBankAccount == null && foundBankAccount != null) {
                newBankAccount = foundBankAccount;
                return newBankAccount;
            }
        }

        throw new WrongAccountIdException(accountId);
    }
}