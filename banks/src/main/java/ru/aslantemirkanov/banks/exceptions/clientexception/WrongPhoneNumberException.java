package ru.aslantemirkanov.banks.exceptions.clientexception;

public class WrongPhoneNumberException extends ClientException {
    public WrongPhoneNumberException(String phoneNumber) {
        super("Phone number " + phoneNumber + " is wrong");
    }
}
