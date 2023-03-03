package ru.aslantemirkanov.banks.models;

import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPhoneNumberException;

public class PhoneNumber {
    private String phoneNumber;

    public PhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber.length() != 11 || !isNumber(newPhoneNumber)) {
            throw new WrongPhoneNumberException(newPhoneNumber);
        }

        phoneNumber = newPhoneNumber;
    }

    public String GetPhoneNumber() {
        return phoneNumber;
    }

    public static boolean isNumber(String str) {
        try {
            for (int i = 0; i < str.length(); ++i) {
                String.valueOf(str.charAt(i));
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
