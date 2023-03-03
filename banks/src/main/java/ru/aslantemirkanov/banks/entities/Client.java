package ru.aslantemirkanov.banks.entities;

import org.jetbrains.annotations.Nullable;
import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPassportSeriesException;
import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPhoneNumberException;
import ru.aslantemirkanov.banks.models.Passport;
import ru.aslantemirkanov.banks.models.PhoneNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {
    @Nullable
    private Passport passport;
    @Nullable
    private PhoneNumber phoneNumber;
    private UUID clientId;
    private String firstName;
    private String secondName;
    private int verificationStatus;
    private List<String> notifications;

    private Client(String newFirstName,
                   String newSecondName,
                   @Nullable Passport newPassport,
                   @Nullable PhoneNumber newPhoneNumber,
                   int newVerificationStatus) {
        clientId = UUID.randomUUID();
        firstName = newFirstName;
        secondName = newSecondName;
        passport = newPassport;
        phoneNumber = newPhoneNumber;
        verificationStatus = newVerificationStatus;
        notifications = new ArrayList<String>();
    }

    public static ClientBuilder builder;

    public void addPassport(String passportSeries) {
        if (passportSeries.isEmpty()) {
            throw new WrongPassportSeriesException(passportSeries);
        }

        passport = new Passport(passportSeries);
        verificationStatus = (phoneNumber == null ? 2 : 3);
    }

    public void addPhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber.isEmpty()) {
            throw new WrongPhoneNumberException(newPhoneNumber);
        }

        phoneNumber = new PhoneNumber(newPhoneNumber);
        verificationStatus = (passport == null ? 1 : 3);
    }

    public void getNotification(String notification) {
        notifications.add(notification);
    }

    public int getVerificationStatus() {
        return verificationStatus;
    }

    public @Nullable PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public @Nullable Passport getPassport() {
        return passport;
    }

    public String getName() {
        return firstName + " " + secondName;
    }

    public UUID getId() {
        return clientId;
    }

    public List<String> showNotifications() {
        return notifications;
    }

    @Override
    public String toString() {
        String res =
                "Name: " + firstName + " " + secondName
                        + "\nVerificationStatus: " + verificationStatus + "\nID: " + clientId;
        res += "\nPassport: " + (passport == null ? "null" : passport.GetPassport());
        res += "\nPhone: " + (phoneNumber == null ? "null" : phoneNumber.GetPhoneNumber());
        return res;
    }

    public static class ClientBuilder {
        private Passport passport;
        private PhoneNumber phoneNumber;
        private String firstName;
        private String secondName;
        private int verificationStatus;

        public ClientBuilder() {
            firstName = null;
            secondName = null;
            passport = null;
            phoneNumber = null;
            verificationStatus = 0;
        }

    public ClientBuilder addFirstName(String newFirstName) {
            firstName = newFirstName;
            return this;
        }

        public ClientBuilder addSecondName(String newSecondName) {
            secondName = newSecondName;
            return this;
        }

        public ClientBuilder addPassport(Passport newPassport) {
            passport = (newPassport.GetPassport().isEmpty() ? null : newPassport);

            return this;
        }

        public ClientBuilder addPhoneNumber(PhoneNumber newPhoneNumber) {
            phoneNumber = (newPhoneNumber.GetPhoneNumber().isEmpty() ? null : newPhoneNumber);

            return this;
        }

        public Client build() {
            if (passport == null && phoneNumber != null) {
                verificationStatus = 1;
            }

            if (passport != null && phoneNumber == null) {
                verificationStatus = 2;
            }

            if (passport != null && phoneNumber != null) {
                verificationStatus = 3;
            }

            return new Client(firstName, secondName, passport, phoneNumber, verificationStatus);
        }
    }

}
