package ru.aslantemirkanov.banks.entities.trancations;

import org.jetbrains.annotations.Nullable;
import ru.aslantemirkanov.banks.entities.bankaccount.BankAccount;
import ru.aslantemirkanov.banks.exceptions.bankexception.NonExistTransactionException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transaction {
    private static Transaction instance;
    private List<TransactionLog> transactions;
    private TransferTransaction transfer;
    private ReplenishmentTransaction replenishment;
    private WithdrawalTransaction withdrawal;

    private Transaction() {
        transactions = new ArrayList<TransactionLog>();
        transfer = new TransferTransaction();
        replenishment = new ReplenishmentTransaction();
        withdrawal = new WithdrawalTransaction();
    }

    public static Transaction getInstance() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }

    public void executeTransaction(
            TransactionType transactionType,
            double money,
            BankAccount accountFrom,
            BankAccount accountTo) {
        switch (transactionType) {
            case Replenishment -> {
                replenishment.transactionExecute(accountFrom, money);
                TransactionLog transactionLog1 =
                        new TransactionLog(accountFrom, accountFrom, money, TransactionType.Replenishment);
                transactions.add(transactionLog1);
                accountFrom.addTransactionLog(transactionLog1);
            }
            case Withdrawal -> {
                withdrawal.transactionExecute(accountFrom, money);
                TransactionLog transactionLog2 =
                        new TransactionLog(accountFrom, accountFrom, money, TransactionType.Withdrawal);
                transactions.add(transactionLog2);
                accountFrom.addTransactionLog(transactionLog2);
            }
            case Transfer -> {
                if (accountTo == null) {
                    throw new RuntimeException();
                }
                transfer.transactionExecute(accountFrom, accountTo, money);
                TransactionLog transactionLog3 =
                        new TransactionLog(accountFrom, accountTo, money, TransactionType.Transfer);
                transactions.add(transactionLog3);
                accountFrom.addTransactionLog(transactionLog3);
                accountTo.addTransactionLog(transactionLog3);
            }
            default -> throw new NonExistTransactionException();
        }
    }

    public void undo(UUID transactionId) throws NonExistTransactionException {
        TransactionLog transactionLog = null;
        for (TransactionLog log : transactions) {
            if (log.getTransactionId().equals(transactionId)) {
                switch (log.getTransactionType()) {
                    case Replenishment -> {
                        log.getAccountFrom().takeOffMoney(log.getTransferAmount());
                        transactionLog = log;
                    }
                    case Withdrawal -> {
                        log.getAccountFrom().fillUpMoney(log.getTransferAmount());
                        transactionLog = log;
                    }
                    case Transfer -> {
                        log.getAccountTo().takeOffMoney(log.getTransferAmount());
                        log.getAccountFrom().fillUpMoney(log.getTransferAmount());
                        transactionLog = log;
                    }
                    default -> throw new NonExistTransactionException();
                }
            }
        }

        if (transactionLog != null) {
            if (transactionLog.getTransactionType() == TransactionType.Replenishment) {
                transactionLog.getAccountFrom().removeTransactionLog(transactionLog);
                transactions.remove(transactionLog);
            }

            if (transactionLog.getTransactionType() == TransactionType.Withdrawal) {
                transactionLog.getAccountFrom().removeTransactionLog(transactionLog);
                transactions.remove(transactionLog);
            }

            if (transactionLog.getTransactionType() == TransactionType.Transfer) {
                transactionLog.getAccountFrom().removeTransactionLog(transactionLog);
                transactionLog.getAccountTo().removeTransactionLog(transactionLog);
                transactions.remove(transactionLog);
            }
        } else {
            throw new NonExistTransactionException();
        }
    }

    public void undoLastTransaction() throws NonExistTransactionException {
        TransactionLog log = transactions.get(transactions.size() - 1);
        switch (log.getTransactionType()) {
            case Replenishment:
                log.getAccountFrom().takeOffMoney(log.getTransferAmount());
                transactions.remove(log);
                break;
            case Withdrawal:
                log.getAccountFrom().fillUpMoney(log.getTransferAmount());
                transactions.remove(log);
                break;
            case Transfer:
                log.getAccountTo().takeOffMoney(log.getTransferAmount());
                log.getAccountFrom().fillUpMoney(log.getTransferAmount());
                transactions.remove(log);
                break;
            default:
                throw new NonExistTransactionException();
        }
    }

}
