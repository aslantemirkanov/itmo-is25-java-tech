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

    public void addPhoneNumberSubscriber(Client client) {
        if (client.getPhoneNumber() == null) {
            throw new TryToGetNullPhoneNumberException(client);
        }

        if (!subscribers.containsKey(client)) {
            subscribers.put(client, new PhoneNotification());
        }
    }

    public void addMailSubscriber(Client client) {
        subscribers.put(client, new MailNotification(subscribers.get(client)));
    }

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


    public void changeDepositInterest(SortedMap<Double, Double> newDepositInterest) {
        depositInterestRates = newDepositInterest;
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountType().equals(AccountType.Deposit)) {
                bankAccount.changeParameter(ChangeType.DepositInterest, getDepositInterestRate(bankAccount.getBalance()));
            }
        }
        sendNotification("Deposit interest changed", AccountType.Deposit);
    }


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

    public void addMonthInterest() {
        for (BankAccount bankAccount : bankAccounts) {
            AccountType accountType = bankAccount.getAccountType();
            if (accountType == AccountType.Debit || accountType == AccountType.Deposit) {
                bankAccount.changeParameter(ChangeType.AddMonthInterest, 0);
            }
        }
    }

    public void addDayInterest() {
        for (BankAccount bankAccount : bankAccounts) {
            AccountType accountType = bankAccount.getAccountType();
            if (accountType == AccountType.Debit || accountType == AccountType.Deposit) {
                bankAccount.changeParameter(ChangeType.AddDayInterest, 0);
            }
        }
    }

    public UUID GetBankId() {
        return bankId;
    }

    public Client getClient(String id) {
        for (Client client : clients.keySet()) {
            if (client.getId().toString().equals(id)) {
                return client;
            }
        }
        return null;
    }

    public List<Client> getBankAllClients() {
        return new ArrayList<Client>(clients.keySet());
    }

    public List<BankAccount> getClientAccounts(Client client) {
        return new ArrayList<BankAccount>(clients.get(client));
    }

    public String getName() {
        return bankName;
    }

    private double getDepositInterestRate(double balance) {
        for (double thresholdBalance : depositInterestRates.keySet()) {
            if (thresholdBalance <= balance) {
                return depositInterestRates.get(thresholdBalance);
            }
        }

        throw new WrongDepositBalanceException(balance);
    }

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

        public BankBuilder addName(String name) {
            _bankName = name;
            return this;
        }

        public BankBuilder addDebitInterestRate(double debitInterestRate) {
            _debitInterestRate = debitInterestRate;
            return this;
        }

        public BankBuilder addCreditInterestRate(double creditInterestRate) {
            _creditInterestRate = creditInterestRate;
            return this;
        }

        public BankBuilder addCreditLimit(double creditLimit) {
            _creditLimit = creditLimit;
            return this;
        }

        public BankBuilder addSuspiciousAccountLimit(double suspiciousAccountLimit) {
            _suspiciousAccountLimit = suspiciousAccountLimit;
            return this;
        }

        public BankBuilder addDepositInterestRate(SortedMap<Double, Double> depositInterestRate) {
            _depositInterestRates = depositInterestRate;
            return this;
        }

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
