package ru.aslantemirkanov.banksconsole;

import ru.aslantemirkanov.banks.entities.Bank;
import ru.aslantemirkanov.banks.entities.Client;
import ru.aslantemirkanov.banks.models.Passport;
import ru.aslantemirkanov.banks.models.PhoneNumber;

import java.util.TreeMap;

public class BanksConsole {
    public static void main(String[] argc) {

        Bank bank1 = new Bank.BankBuilder()
                .addName("alfa")
                .addCreditLimit(1000)
                .addCreditInterestRate(10)
                .addDebitInterestRate(3)
                .addDepositInterestRate(new TreeMap<>() {
                    {
                        put(200.0, 1.0);
                    };
                    {
                        put(100.0, 2.0);
                    }
                })
                .addSuspiciousAccountLimit(400)
                .build();

        Client client1 = new Client.ClientBuilder()
                .addFirstName("aslan")
                .addSecondName("temirkanov")
                .addPassport(new Passport("8317343567"))
                .addPhoneNumber(new PhoneNumber("+79999999999"))
                .build();
    }
}
