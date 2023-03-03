package ru.aslantemirkanov.banks.models;

import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPassportSeriesException;

public class Passport {
    private final String passportSeries;
    public Passport(String newPassportSeries)
    {
        if (newPassportSeries.length() != 10 || !isNumber(newPassportSeries))
        {
            throw new WrongPassportSeriesException(newPassportSeries);
        }

        passportSeries = newPassportSeries;
    }

    public static boolean isNumber(String str) {
        try {
            for (int i = 0 ; i < str.length(); ++i){
                String.valueOf(str.charAt(i));
            }
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public String GetPassport()
    {
        return passportSeries;
    }
}
