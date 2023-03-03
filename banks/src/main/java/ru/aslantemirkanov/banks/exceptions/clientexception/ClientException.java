package ru.aslantemirkanov.banks.exceptions.clientexception;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}
