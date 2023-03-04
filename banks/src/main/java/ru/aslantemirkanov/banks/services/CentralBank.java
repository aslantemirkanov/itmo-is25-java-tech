package ru.aslantemirkanov.banks.services;

import org.jetbrains.annotations.Nullable;
import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;
import ru.aslantemirkanov.banks.entities.trancations.Transaction;
import ru.aslantemirkanov.banks.entities.trancations.TransactionType;
import ru.aslantemirkanov.banks.exceptions.bankexception.WrongAccountIdException;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс CentralBank представляет центральный банк банковской системы.
 * Он отвечает за управление списком зарегистрированных банков и выполнение транзакций между ними.
 */
public class CentralBank {
    private static CentralBank instance;
    private List<Bank> banks;
    private LocalDate currentDate;

    /**
     * Конструктор класса CentralBank
     * Инициализирует список банков и текущую дату
     * Экземпляр класса CentralBank. Он является синглтоном и может иметь только один экземпляр.
     */
    private CentralBank() {
        banks = new ArrayList<>();
        currentDate = LocalDate.now();
    }

    /**
     * Реализация паттерна Одиночка
     * Получить экземпляр центрального банка
     * Возвращает экземпляр класса CentralBank.
     * Если экземпляр равен null, создается новый экземпляр.
     *
     * @return Экземпляр класса CentralBank.
     */
    public static CentralBank getInstance() {
        if (instance == null) {
            instance = new CentralBank();
        }
        return instance;
    }

    /**
     * Выполнить операцию перевода денежных средств между счетами
     *
     * @param accountFromId Идентификатор банковского счета, с которого переводятся денежные средства
     * @param accountToId   Идентификатор банковского счета, на который переводятся денежные средства
     * @param money         Сумма перевода
     */
    public void transferTransaction(UUID accountFromId, UUID accountToId, double money) {
        BankAccount accountFrom = getBankAccount(accountFromId);
        BankAccount accountTo = getBankAccount(accountToId);
        Transaction.getInstance().executeTransaction(TransactionType.Transfer, money, accountFrom, accountTo);
    }

    /**
     * Зарегистрировать новый банк
     *
     * @param bankName               Название банка
     * @param debitInterestRate      Процентная ставка по дебетовому договору
     * @param depositInterestRates   Процентные ставки по депозитным договорам
     * @param creditInterestRate     Процентная ставка по кредиту
     * @param creditLimit            Лимит кредита
     * @param suspiciousAccountLimit Лимит подозрительных аккаунтов
     * @return Зарегистрированный банк
     */
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

    /**
     * Добавление созданного банка в список банков
     *
     * @param bank созданный банк
     */
    public void registerBank(Bank bank) {
        banks.add(bank);
    }

    /**
     * Добавляет проценты за месяц ко всем банкам в центральном банке.
     * Проценты рассчитываются на основании текущих ставок банков.
     */
    public void addMonthInterest() {
        for (Bank bank : banks) {
            bank.addMonthInterest();
        }
    }

    /**
     * Добавляет проценты за день ко всем банкам в центральном банке.
     * Проценты рассчитываются на основании текущих ставок банков.
     * Также увеличивает текущую дату на 1 день.
     */
    public void addDayInterest() {
        for (Bank bank : banks) {
            bank.addDayInterest();
        }
        currentDate = currentDate.plusDays(1);
    }

    /**
     * Возвращает текущую дату центрального банка.
     *
     * @return текущую дату центрального банка
     */
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * Возвращает список всех зарегистрированных банков в центральном банке.
     *
     * @return список всех зарегистрированных банков в центральном банке
     */
    public List<Bank> getBanksList() {
        return banks;
    }

    /**
     * Возвращает банковский аккаунт с указанным идентификатором.
     *
     * @param accountId идентификатор банковского аккаунта
     * @return банковский аккаунт с указанным идентификатором
     * @throws WrongAccountIdException если не удалось найти аккаунт с указанным идентификатором
     */
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