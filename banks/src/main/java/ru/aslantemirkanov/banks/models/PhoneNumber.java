package ru.aslantemirkanov.banks.models;

import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPhoneNumberException;

public class PhoneNumber {
    private String phoneNumber;

    /**
     * Создает новый экземпляр класса PhoneNumber на основе переданного телефонного номера.
     *
     * @param newPhoneNumber строковое значение телефонного номера
     * @throws WrongPhoneNumberException если переданный телефонный номер не соответствует формату, то есть если он не имеет
     *                                   11 цифр
     */
    public PhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber.length() != 11 || !isNumber(newPhoneNumber)) {
            throw new WrongPhoneNumberException(newPhoneNumber);
        }

        phoneNumber = newPhoneNumber;
    }

    /**
     * Возвращает телефонный номер.
     *
     * @return строковое значение телефонного номера
     */
    public String GetPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Проверяет, состоит ли переданная строка только из цифр.
     *
     * @param str строковое значение, которое требуется проверить на соответствие формату
     * @return true, если переданная строка состоит только из цифр; false, в противном случае
     */
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