package ru.aslantemirkanov.banks.models;

import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPassportSeriesException;

/**
 * Класс, представляющий паспорт.
 */
public class Passport {

    /**
     * Серия паспорта, состоящая из 10 цифр.
     */
    private final String passportSeries;

    /**
     * Создает новый экземпляр паспорта с указанной серией.
     *
     * @param newPassportSeries серия паспорта для создания.
     * @throws WrongPassportSeriesException если серия паспорта не состоит из 10 цифр.
     */
    public Passport(String newPassportSeries) throws WrongPassportSeriesException {
        if (newPassportSeries.length() != 10 || !isNumber(newPassportSeries)) {
            throw new WrongPassportSeriesException(newPassportSeries);
        }

        passportSeries = newPassportSeries;
    }

    /**
     * Проверяет, является ли строка числом.
     *
     * @param str строка для проверки.
     * @return true, если строка является числом, иначе false.
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

    /**
     * Возвращает серию паспорта.
     *
     * @return серия паспорта.
     */
    public String GetPassport() {
        return passportSeries;
    }
}