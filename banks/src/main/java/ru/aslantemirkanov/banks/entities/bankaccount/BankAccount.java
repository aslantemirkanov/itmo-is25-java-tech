package ru.aslantemirkanov.banks.entities.bankaccount;

import ru.aslantemirkanov.banks.entities.notifications.ChangeType;
import ru.aslantemirkanov.banks.entities.trancations.TransactionLog;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для представления банковского счета. Содержит методы для управления счетом,
 * включая добавление и снятие денег, получения идентификатора счета, получения типа счета,
 * изменения параметров счета, получения баланса и управления журналом транзакций.
 */
public interface BankAccount {

    /**
     * Пополняет счет на указанную сумму.
     *
     * @param moneyAmount сумма для пополнения
     */
    void fillUpMoney(double moneyAmount);

    /**
     * Снимает со счета указанную сумму.
     *
     * @param moneyAmount сумма для снятия
     */
    void takeOffMoney(double moneyAmount);

    /**
     * Получает идентификатор счета.
     *
     * @return идентификатор счета
     */
    UUID getAccountId();

    /**
     * Получает тип счета.
     *
     * @return тип счета
     */
    AccountType getAccountType();

    /**
     * Изменяет заданный параметр счета на новое значение.
     *
     * @param changeType   тип изменения
     * @param newParameter новое значение параметра
     */
    void changeParameter(ChangeType changeType, double newParameter);

    /**
     * Получает текущий баланс на счете.
     *
     * @return текущий баланс
     */
    double getBalance();

    /**
     * Удаляет заданный журнал транзакций с текущего счета.
     *
     * @param transactionLog журнал транзакций для удаления
     */
    void removeTransactionLog(TransactionLog transactionLog);

    /**
     * Добавляет новый журнал транзакций на текущий счет.
     *
     * @param transactionLog журнал транзакций для добавления
     */
    void addTransactionLog(TransactionLog transactionLog);

    /**
     * Получает список журналов транзакций на текущем счете.
     *
     * @return список журналов транзакций
     */
    List<TransactionLog> getTransactions();
}
