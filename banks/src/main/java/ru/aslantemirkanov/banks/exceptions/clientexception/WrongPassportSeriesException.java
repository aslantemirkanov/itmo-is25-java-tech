package ru.aslantemirkanov.banks.exceptions.clientexception;

public class WrongPassportSeriesException extends ClientException {
    public WrongPassportSeriesException(String passportSeries) {
        super("Passport series " + passportSeries + " is wrong");
    }
}
