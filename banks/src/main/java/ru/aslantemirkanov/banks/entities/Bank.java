package ru.aslantemirkanov.banks.entities;

import ru.aslantemirkanov.banks.entities.bankaccount.*;
import ru.aslantemirkanov.banks.entities.notifications.ChangeType;
import ru.aslantemirkanov.banks.entities.notifications.MailNotification;
import ru.aslantemirkanov.banks.entities.notifications.Notification;
import ru.aslantemirkanov.banks.entities.notifications.PhoneNotification;
import ru.aslantemirkanov.banks.exceptions.bankexception.*;
import ru.aslantemirkanov.banks.exceptions.clientexception.*;
import ru.aslantemirkanov.banks.models.Passport;
import ru.aslantemirkanov.banks.models.PhoneNumber;
import ru.aslantemirkanov.banks.services.CentralBank;
import ru.aslantemirkanov.banks.entities.trancations.*;

import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private UUID bankId;
    private String bankName;
    private Map<Client, List<BankAccount>> clients;
    private Map<Client, Notification> subscribers;
    private List<BankAccount> bankAccounts;
    private double debitInterestRate;
    private double creditInterestRate;
    private double creditLimit;
    private double suspiciousAccountLimit;
    private SortedMap<Double, Double> depositInterestRates;

    /**
     * Конструктор для создания объекта Bank.
     *
     * @param bankName             Название банка.
     * @param debitInterestRate    Процентная ставка по дебетовому счету.
     * @param depositInterestRates Список процентных ставок для депозитов.
     * @param creditInterestRate   Процентная ставка по кредитам.
     * @param creditLimit          Максимальный лимит по кредиту.
     */
    private Bank(
            String bankName,
            double debitInterestRate,
            SortedMap<Double, Double> depositInterestRates,
            double creditInterestRate,
            double creditLimit,
            double suspiciousAccountLimit) {

        this.bankId = UUID.randomUUID();
        this.bankName = bankName;
        this.debitInterestRate = debitInterestRate;
        this.depositInterestRates = depositInterestRates;
        this.creditInterestRate = creditInterestRate;
        this.creditLimit = creditLimit;
        this.suspiciousAccountLimit = suspiciousAccountLimit;
        this.clients = new HashMap<Client, List<BankAccount>>();
        this.subscribers = new HashMap<Client, Notification>();
        this.bankAccounts = new ArrayList<BankAccount>();
    }

    /**
     * Открывает банковский счет для клиента с заданным балансом и типом счета.
     *
     * @param client      Клиент, для которого открывается банковский счет.
     * @param balance     Баланс для открытия счета.
     * @param accountType Тип счета.
     * @return Созданный экземпляр банковского счета.
     * @throws TryToCreateVerificatedAccountException Если тип счета Credit, а клиент не прошел верификацию.
     * @throws NegativeBalanceException               Если заданный баланс отрицательный.
     * @throws NonExistAccountTypeException           Если тип счета не существует.
     */
    public BankAccount openAccount(Client client, double balance, AccountType accountType) {
        double limit = Double.POSITIVE_INFINITY;
        if (client.getVerificationStatus() != 3) {
            if (accountType == AccountType.Credit) {
                throw new TryToCreateVerificatedAccountException();
            }

            limit = this.suspiciousAccountLimit;
        }

        if (balance < 0) {
            throw new NegativeBalanceException();
        }


        BankAccount bankAccount = switch (accountType) {
            case Debit -> new DebitAccount(
                    this,
                    client,
                    balance,
                    this.debitInterestRate,
                    limit,
                    AccountType.Debit);
            case Credit -> new CreditAccount(
                    this,
                    client,
                    this.creditInterestRate,
                    this.creditLimit,
                    balance,
                    AccountType.Deposit);
            case Deposit -> new DepositAccount(
                    this,
                    client,
                    this.getDepositInterestRate(balance),
                    balance,
                    AccountType.Credit);
            default -> throw new NonExistAccountTypeException();
        };

        clients.get(client).add(bankAccount);
        bankAccounts.add(bankAccount);
        client.getNotification(String.format("You opened %s account", accountType));
        return bankAccount;
    }

    /**
     * Добавляет клиента в банк.
     *
     * @param firstName   имя клиента
     * @param secondName  фамилия клиента
     * @param passport    объект паспорта клиента, может быть null
     * @param phoneNumber объект номера телефона клиента, может быть null
     * @return новый клиент
     */
    public Client addClient(String firstName, String secondName, Passport passport, PhoneNumber phoneNumber) {
        Client client = new Client.ClientBuilder()
                .addFirstName(firstName)
                .addSecondName(secondName)
                .addPassport(passport != null ? passport : new Passport(""))
                .addPhoneNumber(phoneNumber != null ? phoneNumber : new PhoneNumber(""))
                .build();

        clients.put(client, new ArrayList<>());
        return client;
    }

    public void addClient(Client client) {
        clients.put(client, new ArrayList<>());
    }

    /**
     * Добавляет номер паспорта клиента в его данные и проверяет статус верификации. Если клиент становится верифицированным,
     * то у всех его дебетовых счетов изменяется лимит на бесконечность.
     *
     * @param client   Клиент, которому нужно добавить номер паспорта.
     * @param passport Номер паспорта клиента.
     */
    public void addClientPassport(Client client, String passport) {
        client.addPassport(passport);
        if (client.getVerificationStatus() == 3) {
            for (BankAccount bankAccount : clients.get(client)) {
                if (bankAccount.getAccountType().equals(AccountType.Debit)) {
                    bankAccount.changeParameter(ChangeType.CreditLimit, Double.POSITIVE_INFINITY);
                }
            }
        }
    }

    /**
     * Добавляет номер телефона клиента в его данные и проверяет статус верификации.
     *
     * @param client      Клиент, которому нужно добавить номер телефона.
     * @param phoneNumber Номер телефона клиента.
     */
    public void addClientPhoneNumber(Client client, String phoneNumber) {
        client.addPhoneNumber(phoneNumber);
        if (client.getVerificationStatus() == 3) {
            for (BankAccount bankAccount : clients.get(client)) {
                if (bankAccount.getAccountType().equals(AccountType.Debit)) {
                    bankAccount.changeParameter(ChangeType.CreditLimit, Double.POSITIVE_INFINITY);
                }
            }
        }
    }

    /**
     * Добавляет клиента в список подписчиков на уведомления по номеру телефона. Если клиент уже был добавлен в список,
     * то метод не выполняет никаких действий.
     *
     * @param client Клиент, которого нужно добавить в список подписчиков.
     */
    public void addPhoneNumberSubscriber(Client client) {
        if (client.getPhoneNumber() == null) {
            throw new TryToGetNullPhoneNumberException(client);
        }

        if (!subscribers.containsKey(client)) {
            subscribers.put(client, new PhoneNotification());
        }
    }

    /**
     * Добавляет клиента в список подписчиков на уведомления по почте. Если клиент уже был добавлен в список,
     * то метод не выполняет никаких действий.
     *
     * @param client Клиент, которого нужно добавить в список подписчиков.
     */
    public void addMailSubscriber(Client client) {
        subscribers.put(client, new MailNotification(subscribers.get(client)));
    }

    /**
     * Отправляет уведомления всем счетам заданного типа
     *
     * @param message     Сообщение в уведомлении
     * @param accountType Тип счета, на который отправляется уведомление
     */
    public void sendNotification(String message, AccountType accountType) {
        for (Client client : subscribers.keySet()) {
            for (BankAccount bankAccount : clients.get(client)) {
                if (bankAccount.getAccountType().equals(accountType)) {
                    subscribers.get(client).send(client, message);
                    break;
                }
            }
        }
    }

    /**
     * Изменяет процентную ставку по кредиту для банка и всех его клиентов, у которых открыт кредитный счет.
     *
     * @param newCreditInterest Новая процентная ставка по кредиту.
     */
    public void changeCreditInterest(double newCreditInterest) {
        creditInterestRate = newCreditInterest;
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountType().equals(AccountType.Credit)) {
                bankAccount.changeParameter(ChangeType.CreditInterest, newCreditInterest);
            }
        }
        sendNotification(String.format("Credit interest changed from %f to %f",
                        creditInterestRate,
                        newCreditInterest),
                AccountType.Credit);
    }


    /**
     * Изменяет лимит по кредиту для банка и всех его клиентов, у которых открыт кредитный счет.
     *
     * @param newCreditLimit Новый лимит по кредитам
     */

    public void changeCreditLimit(double newCreditLimit) {
        creditLimit = newCreditLimit;
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountType().equals(AccountType.Credit)) {
                bankAccount.changeParameter(ChangeType.CreditLimit, newCreditLimit);
            }
        }
        sendNotification(String.format("Credit limit changed from %f to %f",
                        creditLimit,
                        newCreditLimit),
                AccountType.Credit);
    }

    /**
     * Изменяем ставку по дебетовому счету
     *
     * @param newDebitInterest новая процентная ставка
     */
    public void changeDebitInterest(double newDebitInterest) {
        debitInterestRate = newDebitInterest;
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountType().equals(AccountType.Debit)) {
                bankAccount.changeParameter(ChangeType.DebitInterest, newDebitInterest);
            }
        }
        sendNotification(String.format("Debit interest changed from %f to %f",
                        debitInterestRate,
                        newDebitInterest),
                AccountType.Debit);
    }


    /**
     * Изменяем ставку по депозитному счету
     *
     * @param newDepositInterest новая ставка по депозитному счету
     */
    public void changeDepositInterest(SortedMap<Double, Double> newDepositInterest) {
        depositInterestRates = newDepositInterest;
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountType().equals(AccountType.Deposit)) {
                bankAccount.changeParameter(ChangeType.DepositInterest, getDepositInterestRate(bankAccount.getBalance()));
            }
        }
        sendNotification("Deposit interest changed", AccountType.Deposit);
    }


    /**
     * Получаем банковский счет по заданному идентефикатору
     *
     * @param accountId
     * @return банковский счет по заданному UUID
     */
    public BankAccount getBankAccount(UUID accountId) {
        BankAccount newBankAccount = null;
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountId().equals(accountId)) {
                newBankAccount = bankAccount;
                break;
            }
        }

        return newBankAccount;
    }


    /**
     * Пополняем заданный счет на заданную сумму
     *
     * @param accountId уникальный номер счета
     * @param money     сумма пополнения
     */
    public void replenishmentTransaction(UUID accountId, double money) {
        BankAccount newBankAccount = getBankAccount(accountId);
        if (newBankAccount == null) {
            throw new WrongAccountIdException(accountId);
        }

        Transaction.getInstance().executeTransaction(
                TransactionType.Replenishment,
                money,
                newBankAccount,
                newBankAccount);
    }

    /**
     * Выводим заданную сумму с указанного счета
     *
     * @param accountId уникальный номер счета
     * @param money     сумма вывода
     */
    public void withdrawalTransaction(UUID accountId, double money) {
        BankAccount newBankAccount = getBankAccount(accountId);
        if (newBankAccount == null) {
            throw new WrongAccountIdException(accountId);
        }
        Transaction.getInstance().executeTransaction(
                TransactionType.Withdrawal,
                money,
                newBankAccount,
                newBankAccount);
    }

    /**
     * Осуществляем переводы между банками
     *
     * @param accountFromId номер счета откуда снимаем деньги
     * @param accountToId   номер счета куда переводим деньги
     * @param money         сумма перевода
     */
    public void transferTransaction(UUID accountFromId, UUID accountToId, double money) {
        BankAccount accountFrom = getBankAccount(accountFromId);
        BankAccount accountTo = getBankAccount(accountToId);
        if (accountFrom != null && accountTo != null) {
            Transaction.getInstance().executeTransaction(
                    TransactionType.Transfer,
                    money,
                    accountFrom,
                    accountTo);
        } else {
            CentralBank.getInstance().transferTransaction(accountFromId, accountToId, money);
        }
    }


    /**
     * Осуществляем ежемесячное пополнение счетов на процентную ставку
     */
    public void addMonthInterest() {
        for (BankAccount bankAccount : bankAccounts) {
            AccountType accountType = bankAccount.getAccountType();
            if (accountType == AccountType.Debit || accountType == AccountType.Deposit) {
                bankAccount.changeParameter(ChangeType.AddMonthInterest, 0);
            }
        }
    }

    /**
     * Осуществляем ежедневное пополнение счетов на процентую ставку
     */
    public void addDayInterest() {
        for (BankAccount bankAccount : bankAccounts) {
            AccountType accountType = bankAccount.getAccountType();
            if (accountType == AccountType.Debit || accountType == AccountType.Deposit) {
                bankAccount.changeParameter(ChangeType.AddDayInterest, 0);
            }
        }
    }

    /**
     * Получаем уникальный номер банка
     *
     * @return уникальный номер банка
     */
    public UUID GetBankId() {
        return bankId;
    }

    /**
     * Получаем клиента по его уникальному номеру
     *
     * @param id номер уникального
     * @return клиента по заданному уникальному номеру
     */
    public Client getClient(String id) {
        for (Client client : clients.keySet()) {
            if (client.getId().toString().equals(id)) {
                return client;
            }
        }
        return null;
    }

    /**
     * получаем список всех клиентов банка
     *
     * @return список клиентов банка
     */
    public List<Client> getBankAllClients() {
        return new ArrayList<Client>(clients.keySet());
    }

    /**
     * получаем список всех счетов клиента в банке
     *
     * @param client клиент, счета которого нужны
     * @return список счетов
     */
    public List<BankAccount> getClientAccounts(Client client) {
        return new ArrayList<BankAccount>(clients.get(client));
    }

    /**
     * получаем имя банка
     *
     * @return имя банка
     */
    public String getName() {
        return bankName;
    }

    /**
     * получаем подходящую процентную ставку для нового депозитного счета
     * @param balance баланс нового счета
     * @return процентая ставка
     */
    private double getDepositInterestRate(double balance) {
        for (double thresholdBalance : depositInterestRates.keySet()) {
            if (thresholdBalance <= balance) {
                return depositInterestRates.get(thresholdBalance);
            }
        }

        throw new WrongDepositBalanceException(balance);
    }

    /**
     * Паттерн билдер для банка
     */
    public static class BankBuilder {
        private UUID _bankId;
        private String _bankName;
        private Map<Client, List<BankAccount>> _clients;
        private Map<Client, Notification> _subscribers;
        private List<BankAccount> _bankAccounts;
        private double _debitInterestRate;
        private double _creditInterestRate;
        private double _creditLimit;
        private double _suspiciousAccountLimit;
        private SortedMap<Double, Double> _depositInterestRates;

        public BankBuilder() {
            _bankId = UUID.randomUUID();
            _bankName = "";
            _clients = new HashMap<>();
            _subscribers = new HashMap<>();
            _bankAccounts = new ArrayList<>();
            _depositInterestRates = new TreeMap<>();
        }

        /**
         * добавляем имя в билдер
         * @param name имя банка
         * @return объект билдера
         */
        public BankBuilder addName(String name) {
            _bankName = name;
            return this;
        }

        /**
         * добавляем процентную ставку по дебетовому счета
         * @param debitInterestRate процентая ставка
         * @return объект билдера
         */
        public BankBuilder addDebitInterestRate(double debitInterestRate) {
            _debitInterestRate = debitInterestRate;
            return this;
        }

        /**
         * добавялем процентную ставку по кредтному счету
         * @param creditInterestRate процентная ставка
         * @return объект билдера
         */
        public BankBuilder addCreditInterestRate(double creditInterestRate) {
            _creditInterestRate = creditInterestRate;
            return this;
        }

        /**
         * добавляем кредитный лимит
         * @param creditLimit кредитный лимит
         * @return объект билдера
         */
        public BankBuilder addCreditLimit(double creditLimit) {
            _creditLimit = creditLimit;
            return this;
        }

        /**
         * добавляем
         * @param suspiciousAccountLimit
         * @return объект билдера
         */
        public BankBuilder addSuspiciousAccountLimit(double suspiciousAccountLimit) {
            _suspiciousAccountLimit = suspiciousAccountLimit;
            return this;
        }

        /**
         * добавляем процентую ставку по депозитному счету
         * @param depositInterestRate поцентная ставка
         * @return объект билера
         */
        public BankBuilder addDepositInterestRate(SortedMap<Double, Double> depositInterestRate) {
            _depositInterestRates = depositInterestRate;
            return this;
        }

        /**
         * создаем непосредственно сам банк
         * @return новый объект банка
         */
        public Bank build() {
            return new Bank(
                    _bankName,
                    _debitInterestRate,
                    _depositInterestRates,
                    _creditInterestRate,
                    _creditLimit,
                    _suspiciousAccountLimit
            );
        }
    }

}
