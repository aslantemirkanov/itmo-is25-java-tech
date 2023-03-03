package bankstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.Client;
import ru.aslantemirkanov.banks.entities.trancations.*;
import ru.aslantemirkanov.banks.models.Passport;
import ru.aslantemirkanov.banks.models.PhoneNumber;
import ru.aslantemirkanov.banks.services.CentralBank;
import ru.aslantemirkanov.banks.entities.notifications.*;
import ru.aslantemirkanov.banks.entities.bankaccount.*;

import java.util.TreeMap;

public class BanksTests {
/*

    private final CentralBank centralBank = CentralBank.getInstance();
    private final Transaction transacation = Transaction.getInstance();

    private final Bank bank1 = new Bank.BankBuilder()
            .addName("alfa")
            .addCreditLimit(1000)
            .addCreditInterestRate(10)
            .addDebitInterestRate(3)
            .addDepositInterestRate(new TreeMap<>() {{
                put(200.0, 1.0);
            }})
            .addSuspiciousAccountLimit(400)
            .build();

    private final Bank bank2 = new Bank.BankBuilder()
            .addName("sber")
            .addCreditLimit(1000)
            .addCreditInterestRate(10)
            .addDebitInterestRate(3)
            .addDepositInterestRate(new TreeMap<>() {{
                put(200.0, 1.0);
            }})
            .addSuspiciousAccountLimit(400)
            .build();

    private final Client client1 = Client.builder
            .addFirstName("aslan")
            .addSecondName("temirkanov")
            .addPassport(new Passport("7777777777"))
            .addPhoneNumber(new PhoneNumber("+79999999999"))
            .build();

    private final Client client2 = Client.builder
            .addFirstName("daddy")
            .addSecondName("petrov")
            .addPassport(new Passport("7777777777"))
            .addPhoneNumber(new PhoneNumber("+79999999999"))
            .build();

    @Test
    public void transferInBank() {
        centralBank.registerBank(bank1);
        bank1.addClient(client1);
        bank1.addClient(client2);
        var account1 = bank1.openAccount(client1, 2000, AccountType.Credit);
        var account2 = bank1.openAccount(client2, 1000, AccountType.Deposit);
        bank1.transferTransaction(account1.getAccountId(), account2.getAccountId(), 2990);
        Assertions.assertEquals(3990, account2.getBalance());
    }

    @Test
    public void transferBetweenBank() {
        centralBank.registerBank(bank1);
        centralBank.registerBank(bank2);
        bank1.addClient(client1);
        bank2.addClient(client2);
        var account1 = bank1.openAccount(client1, 2000, AccountType.Deposit);
        bank1.replenishmentTransaction(account1.getAccountId(), 2000);
        var account2 = bank2.openAccount(client2, 1000, AccountType.Debit);
        bank1.transferTransaction(account1.getAccountId(), account2.getAccountId(), 100);
        Assertions.assertEquals(1100, account2.getBalance());
        Assertions.assertEquals(3900, account1.getBalance());
    }
*/

}