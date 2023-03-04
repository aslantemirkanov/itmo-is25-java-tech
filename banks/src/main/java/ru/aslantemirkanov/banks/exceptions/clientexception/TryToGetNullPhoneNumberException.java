package ru.aslantemirkanov.banks.exceptions.clientexception;
import ru.aslantemirkanov.banks.entities.Client;

public class TryToGetNullPhoneNumberException extends ClientException {
    public TryToGetNullPhoneNumberException(Client client) {
        super(client.getName() + " don't have phone number");
    }
}
