package ru.aslantemirkanov.banksconsole;

import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.Client;
import ru.aslantemirkanov.banks.entities.MyTimer;
import ru.aslantemirkanov.banks.entities.bankaccount.AccountType;
import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;
import ru.aslantemirkanov.banks.entities.trancations.Transaction;
import ru.aslantemirkanov.banks.entities.trancations.TransactionLog;
import ru.aslantemirkanov.banks.exceptions.bankexception.*;
import ru.aslantemirkanov.banks.exceptions.clientexception.TryToGetNullPhoneNumberException;
import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPassportSeriesException;
import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPhoneNumberException;
import ru.aslantemirkanov.banks.models.Passport;
import ru.aslantemirkanov.banks.models.PhoneNumber;
import ru.aslantemirkanov.banks.services.CentralBank;
import ru.aslantemirkanov.banks.entities.trancations.Transaction;

import java.util.*;

public class BanksConsole {
    private static void welcomeMenu() {
        System.out.println("Welcome to bank system!");
        System.out.println("1 - Create bank");
        System.out.println("2 - Show banks");
        System.out.println("3 - Add monthly interest");
        System.out.println("4 - Choose concrete bank");
        System.out.println("5 - Open bank menu");
        System.out.println("6 - Enter the number of days to speed up");
        System.out.println("7 - Exit");
    }

    private static void welcomeConcreteBankMenu(String bankName) {
        System.out.println("Welcome to bank " + bankName + " system!");
        System.out.println("1 - Create client");
        System.out.println("2 - Choose client");
        System.out.println("3 - Open current client Menu");
        System.out.println("4 - Show all clients of this bank");
        System.out.println("5 - Change Credit Interest");
        System.out.println("6 - Change Credit Limit");
        System.out.println("7 - Change Debit Interest");
        System.out.println("8 - Exit");
    }

    private static void welcomeConcreteClientMenu(String clientName) {
        System.out.println("Hello, " + clientName + "!");
        System.out.println("1 - Add passport");
        System.out.println("2 - Add phone number");
        System.out.println("3 - Create account");
        System.out.println("4 - Subscribe for phone notification");
        System.out.println("5 - Make transaction");
        System.out.println("6 - Undo transaction");
        System.out.println("7 - Show all info about clients accounts");
        System.out.println("8 - Show account transactions Id");
        System.out.println("9 - Show notifications");
        System.out.println("10 - Show client's info");
        System.out.println("11 - Exit");
    }

    public static void main(String[] argc) {
        var centralBank = CentralBank.getInstance();
        Client aslan = new Client
                .ClientBuilder()
                .addFirstName("aslan")
                .addSecondName("temirkan")
                .addPassport(new Passport("1111111111"))
                .addPhoneNumber(new PhoneNumber("89969174661"))
                .build();
        Client daddy = new Client
                .ClientBuilder()
                .addFirstName("daddy")
                .addSecondName("dad")
                .addPassport(new Passport("2222222222"))
                .addPhoneNumber(new PhoneNumber("89969174661"))
                .build();
        Bank alfaBank = new Bank.BankBuilder()
                .addName("Alfa-Bank")
                .addCreditLimit(1000)
                .addCreditInterestRate(10)
                .addDebitInterestRate(3)
                .addDepositInterestRate(new TreeMap<>() {
                    {
                        put(200.0, 1.0);
                    }

                    ;

                    {
                        put(400.0, 2.0);
                    }

                    ;

                    {
                        put(600.0, 3.0);
                    }
                })
                .addSuspiciousAccountLimit(400)
                .build();
        alfaBank.addClient(aslan);
        BankAccount aslanAcc = alfaBank.openAccount(aslan, 1000, AccountType.Debit);
        System.out.println(aslanAcc.getAccountId());
        centralBank.registerBank(alfaBank);
        alfaBank.replenishmentTransaction(aslanAcc.getAccountId(), 1000);
        List<TransactionLog> transactionLogs = aslanAcc.getTransactions();
        Transaction.getInstance().undo(transactionLogs.get(transactionLogs.size() - 1).getTransactionId());
        Bank tinkoffBank = new Bank.BankBuilder()
                .addName("Tinkoff-Bank")
                .addCreditLimit(2000)
                .addCreditInterestRate(20)
                .addDebitInterestRate(5)
                .addDepositInterestRate(new TreeMap<>() {
                    {
                        put(100.0, 2.0);
                    }

                    ;

                    {
                        put(200.0, 4.0);
                    }

                    ;

                    {
                        put(300.0, 6.0);
                    }

                    ;
                })
                .addSuspiciousAccountLimit(400)
                .build();
        tinkoffBank.addClient(daddy);
        var daddyAcc = tinkoffBank.openAccount(daddy, 2000, AccountType.Debit);
        System.out.println(daddyAcc.getAccountId());
        centralBank.registerBank(tinkoffBank);
        Bank currentBank = null;
        boolean isFinished = false;
        Scanner scanner = new Scanner(System.in);
        while (!isFinished) {
            System.out.println();
            welcomeMenu();
            String inputCommand = scanner.nextLine();

            switch (inputCommand) {
                case "1" -> {
                    Bank.BankBuilder bankBuilder = new Bank.BankBuilder();
                    System.out.println("Input bank name:");
                    bankBuilder.addName(scanner.nextLine());
                    System.out.println("Input Debit Interest Rate");
                    bankBuilder.addDebitInterestRate(Double.parseDouble(scanner.nextLine()));
                    System.out.println("Input Credit Interest Rate");
                    bankBuilder.addCreditInterestRate(Double.parseDouble(scanner.nextLine()));
                    System.out.println("Input Credit Limit");
                    bankBuilder.addCreditLimit(Double.parseDouble(scanner.nextLine()));
                    System.out.println("Input Suspicious Account Limit");
                    bankBuilder.addSuspiciousAccountLimit(Double.parseDouble(scanner.nextLine()));
                    SortedMap<Double, Double> deposit = new TreeMap<>();
                    while (true) {
                        System.out.println(">Input minimal value for deposit rate:");
                        double minValue = Double.parseDouble(scanner.nextLine());
                        System.out.println(">Input interest rate for this minimal value:");
                        double interestRate = Double.parseDouble(scanner.nextLine());
                        deposit.put(minValue, interestRate);
                        System.out.println(">One more Rate? [y/n]");
                        String cont = scanner.nextLine();
                        if (cont.equalsIgnoreCase("n")) {
                            break;
                        }
                    }
                    bankBuilder.addDepositInterestRate(deposit);
                    Bank newBank = bankBuilder.build();
                    centralBank.registerBank(newBank);
                    System.out.printf("You created bank %s with Id = %d\n", newBank.getName(), newBank.GetBankId());
                }
                case "2" -> {
                    for (Bank curBank : centralBank.getBanksList()) {
                        System.out.printf("Name: " +
                                curBank.getName() +
                                "\nId: " +
                                curBank.GetBankId().toString()
                                + "\n");
                    }
                }
                case "3" -> {
                    centralBank.addMonthInterest();
                    System.out.println("Interest payed");
                }
                case "4" -> {
                    System.out.println("Input bank Id to chose it");
                    String id = scanner.nextLine();
                    for (Bank bank : centralBank.getBanksList()) {
                        if (Objects.equals(id, bank.GetBankId().toString())) {
                            currentBank = bank;
                        }
                    }
                    if (currentBank == null) {
                        System.out.println("You didn't chose bank");
                    }
                }
                case "5" -> {
                    if (currentBank == null) {
                        System.out.println("You didn't chose bank");
                        break;
                    }
                    concreteBankMenu(currentBank);
                }
                case "6" -> {
                    int dayCount = Integer.parseInt(scanner.nextLine());
                    MyTimer timer = new MyTimer();
                    timer.rewindTime(dayCount);
                }
                case "7" -> isFinished = true;
            }
        }
    }

    private static void concreteBankMenu(Bank bank) {
        Client currentClient = null;
        boolean isFinished = false;
        Scanner scanner = new Scanner(System.in);
        while (!isFinished) {
            System.out.println("\n");
            welcomeConcreteBankMenu(bank.getName());
            String inputCommand = scanner.nextLine();
            switch (inputCommand) {
                case "1":
                    Client.ClientBuilder builder = new Client.ClientBuilder();
                    System.out.println("Input client first name:");
                    builder.addFirstName(scanner.nextLine());
                    System.out.println("Input client second name:");
                    builder.addSecondName(scanner.nextLine());
                    System.out.println("Do you want to add passport? y/n");
                    String answer = scanner.nextLine();
                    if (answer.equals("y")) {
                        boolean isPassportAdded = false;
                        while (!isPassportAdded) {
                            System.out.println("Input passport number:");
                            String pas = scanner.nextLine();
                            try {
                                builder.addPassport(new Passport(pas));
                                isPassportAdded = true;
                            } catch (WrongPassportSeriesException a) {
                                System.out.println(a.getMessage());
                            }
                        }
                    }

                    System.out.println("Do you want to add phone number? y/n");
                    answer = scanner.nextLine();
                    if (answer.equals("y")) {
                        boolean isPhoneAdded = false;
                        while (!isPhoneAdded) {
                            System.out.println("Input phone number:");
                            String phone = scanner.nextLine();
                            try {
                                builder.addPhoneNumber(new PhoneNumber(phone));
                                isPhoneAdded = true;
                            } catch (WrongPhoneNumberException a) {
                                System.out.println(a.getMessage());
                            }
                        }
                    }

                    Client newClient = builder.build();
                    bank.addClient(newClient);
                    System.out.println(
                            "You created client " + newClient.getName() + " with Id " + newClient.getId());
                    break;
                case "2":
                    System.out.println("Input client Id to chose it");
                    String id = scanner.nextLine();
                    Client foundClient = bank.getClient(id);

                    if (foundClient == null) {
                        System.out.println("You enter wrong client id");
                        break;
                    }

                    currentClient = foundClient;
                    break;
                case "3":
                    if (currentClient == null) {
                        System.out.println("You didn't chose client");
                        break;
                    }

                    concreteClientMenu(bank, currentClient);
                    break;
                case "4":
                    System.out.println("All clients of bank " + bank.getName() + ":");
                    for (Client client : bank.getBankAllClients()) {
                        System.out.println("Name: " + client.getName() + " ; Id: " + client.getId());
                    }

                    break;
                case "5":
                    System.out.println("Input new credit interest");
                    double newCreditInterest = Double.parseDouble(scanner.nextLine());
                    bank.changeCreditInterest(newCreditInterest);
                    break;
                case "6":
                    System.out.println("Input new credit limit");
                    double newCreditLimit = Double.parseDouble(scanner.nextLine());
                    bank.changeCreditLimit(newCreditLimit);
                    break;
                case "7":
                    System.out.println("Input new debit interest");
                    double newDebitInterest = Double.parseDouble(scanner.nextLine());
                    bank.changeDebitInterest(newDebitInterest);
                    break;
                case "8":
                    isFinished = true;
                    break;
            }
        }
    }

    private static void concreteClientMenu(Bank bank, Client client) {
        boolean isFinished = false;
        Scanner scanner = new Scanner(System.in);
        while (!isFinished) {
            System.out.println("\n");
            welcomeConcreteClientMenu(client.getName());
            String inputCommand = scanner.nextLine();
            switch (inputCommand) {
                case "1":
                    System.out.println("Input new passport number");
                    String passport = scanner.nextLine();
                    try {
                        bank.addClientPassport(client, passport);
                        System.out.println("You added passport " + passport);
                    } catch (WrongPassportSeriesException a) {
                        System.out.println(a.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("Input new phone number");
                    String phone = scanner.nextLine();
                    try {
                        bank.addClientPhoneNumber(client, phone);
                        System.out.println("You added phone " + phone);
                    } catch (WrongPhoneNumberException a) {
                        System.out.println(a.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("Input balance:");
                    double balance = scanner.nextDouble();
                    System.out.println("1 - Debit account");
                    System.out.println("2 - Deposit account");
                    System.out.println("3 - Credit account");
                    String res = scanner.nextLine();
                    BankAccount bankAccount = null;
                    switch (res) {
                        case "1":
                            try {
                                bankAccount = bank.openAccount(client, balance, AccountType.Debit);
                            } catch (NegativeBalanceException b) {
                                System.out.println(b.getMessage());
                            }
                            break;
                        case "2":
                            try {
                                bankAccount = bank.openAccount(client, balance, AccountType.Deposit);
                            } catch (NegativeBalanceException b) {
                                System.out.println(b.getMessage());
                            } catch (WrongDepositBalanceException a) {
                                System.out.println(a.getMessage());
                            }
                            break;
                        case "3":
                            try {
                                bankAccount = bank.openAccount(client, balance, AccountType.Credit);
                            } catch (TryToCreateVerificatedAccountException a) {
                                System.out.println(a.getMessage());
                            } catch (NegativeBalanceException b) {
                                System.out.println(b.getMessage());
                            }
                            break;
                    }
                    System.out.print("You opened " + bankAccount.getAccountType().toString() + " account ");
                    System.out.println("with Id = " + bankAccount.getAccountId());
                    break;
                case "4":
                    try {
                        bank.addPhoneNumberSubscriber(client);
                        System.out.println("You subscribed for phone notifications");
                    } catch (TryToGetNullPhoneNumberException a) {
                        System.out.println(a.getMessage());
                    }
                    break;
                case "5":
                    System.out.println("1 - Transfer");
                    System.out.println("2 - Replenishment");
                    System.out.println("3 - Withdrawal");
                    String ans = scanner.nextLine();
                    switch (ans) {
                        case "1":
                            System.out.println("Input the sender's account id");
                            UUID accountFrom = UUID.fromString(scanner.nextLine());
                            System.out.println("Input the receiver's account id");
                            UUID accountTo = UUID.fromString(scanner.nextLine());
                            System.out.println("Input the transfer amount");
                            double moneyTransfer = Double.parseDouble(scanner.nextLine());
                            try {
                                bank.transferTransaction(accountFrom, accountTo, moneyTransfer);
                                System.out.println("You transferred " + moneyTransfer + " USD");
                            } catch (ExtraWithdrawalLimitException | NegativeBalanceException |
                                     WrongAccountIdException | CreditLimitExcessException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "2":
                            System.out.println("Input the account id");
                            UUID accountId = UUID.fromString(scanner.nextLine());
                            System.out.println("Input the replenishment amount");
                            double moneyReplenishment = Double.parseDouble(scanner.nextLine());
                            try {
                                bank.replenishmentTransaction(accountId, moneyReplenishment);
                                System.out.println("You filled up for " + moneyReplenishment + " USD");
                            } catch (ExtraWithdrawalLimitException | NegativeBalanceException |
                                     WrongAccountIdException | CreditLimitExcessException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "3":
                            System.out.println("Input the account id");
                            UUID accId = UUID.fromString(scanner.nextLine());
                            System.out.println("Input the withdrawal amount");
                            double moneyWithdrawal = Double.parseDouble(scanner.nextLine());
                            try {
                                bank.withdrawalTransaction(accId, moneyWithdrawal);
                                System.out.println("You took off " + moneyWithdrawal + " USD");
                            } catch (ExtraWithdrawalLimitException | NegativeBalanceException |
                                     WrongAccountIdException | CreditLimitExcessException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                    }
                    break;
                case "6":
                    System.out.println("Input transaction id");
                    UUID transactionId = UUID.fromString(scanner.nextLine());
                    try {
                        Transaction.getInstance().undo(transactionId);
                    } catch (NonExistTransactionException | ExtraWithdrawalLimitException | NegativeBalanceException |
                             WrongAccountIdException | CreditLimitExcessException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "7":
                    for (BankAccount bankAcc : bank.getClientAccounts(client)) {
                        System.out.println("Id = " + bankAcc.getAccountId() + "  balance = " + bankAcc.getBalance());
                    }
                    break;
                case "8":
                    System.out.println("Input account Id");
                    UUID accIdForTransactions = UUID.fromString(scanner.nextLine());
                    BankAccount account = bank.getBankAccount(accIdForTransactions);
                    if (account == null) {
                        System.out.println("Client don't have that account");
                    } else {
                        List<TransactionLog> transactions = account.getTransactions();
                        for (TransactionLog transactionLog : transactions) {
                            System.out.println("Id = " + transactionLog.getTransactionId() + " type = " + transactionLog.getTransactionType());
                        }
                    }
                case "9":
                    List<String> notifications = client.showNotifications();
                    for (String notification : notifications) {
                        System.out.println(notification);
                    }
                    break;
                case "10":
                    System.out.println(client);
                    break;
                case "11":
                    isFinished = true;
                    break;
            }
        }
    }
}