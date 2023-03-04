package ru.aslantemirkanov.banks.entities;

import org.jetbrains.annotations.Nullable;
import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPassportSeriesException;
import ru.aslantemirkanov.banks.exceptions.clientexception.WrongPhoneNumberException;
import ru.aslantemirkanov.banks.models.Passport;
import ru.aslantemirkanov.banks.models.PhoneNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Класс Client представляет собой клиента банка.
 * У клиента есть паспорт, номер телефона, уникальный идентификатор, имя, фамилия, статус верификации и список уведомлений.
 */
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

    /**
     * Создает нового клиента с заданными именем, фамилией, паспортом, номером телефона и статусом верификации.
     *
     * @param newFirstName          имя клиента
     * @param newSecondName         фамилия клиента
     * @param newPassport           паспорт клиента
     * @param newPhoneNumber        номер телефона клиента
     * @param newVerificationStatus статус верификации клиента
     */
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

    /**
     * Статический класс-строитель для создания новых клиентов.
     */
    public static ClientBuilder builder;

    /**
     * Добавляет паспорт клиенту с заданным номером серии.
     *
     * @param passportSeries номер серии паспорта клиента
     * @throws WrongPassportSeriesException если номер серии паспорта некорректен
     */
    public void addPassport(String passportSeries) {
        if (passportSeries.isEmpty()) {
            throw new WrongPassportSeriesException(passportSeries);
        }

        passport = new Passport(passportSeries);
        verificationStatus = (phoneNumber == null ? 2 : 3);
    }

    /**
     * Добавляет номер телефона клиенту.
     *
     * @param newPhoneNumber номер телефона клиента
     * @throws WrongPhoneNumberException если номер телефона некорректен
     */
    public void addPhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber.isEmpty()) {
            throw new WrongPhoneNumberException(newPhoneNumber);
        }

        phoneNumber = new PhoneNumber(newPhoneNumber);
        verificationStatus = (passport == null ? 1 : 3);
    }

    /**
     * Добавляет уведомление для клиента.
     *
     * @param notification уведомление для клиента
     */
    public void getNotification(String notification) {
        notifications.add(notification);
    }

    /**
     * Возвращает статус верификации клиента.
     *
     * @return статус верификации клиента
     */
    public int getVerificationStatus() {
        return verificationStatus;
    }

    /**
     * Возвращает номер телефона клиента.
     *
     * @return номер телефона клиента
     */
    public @Nullable PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Возвращает паспорт клиента.
     *
     * @return паспорт клиента
     */
    public @Nullable Passport getPassport() {
        return passport;
    }

    /**
     * Возвращает полное имя клиента.
     *
     * @return полное имя клиента в формате "firstName secondName"
     */
    public String getName() {
        return firstName + " " + secondName;
    }

    /**
     * Возвращает идентификатор клиента.
     *
     * @return идентификатор клиента в формате UUID
     */
    public UUID getId() {
        return clientId;
    }

    /**
     * Возвращает список уведомлений клиента.
     *
     * @return список уведомлений клиента в формате List<String>
     */
    public List<String> showNotifications() {
        return notifications;
    }

    /**
     * Возвращает строковое представление объекта клиента.
     *
     * @return строковое представление объекта клиента
     */
    @Override
    public String toString() {
        String res =
                "Name: " + firstName + " " + secondName
                        + "\nVerificationStatus: " + verificationStatus + "\nID: " + clientId;
        res += "\nPassport: " + (passport == null ? "null" : passport.GetPassport());
        res += "\nPhone: " + (phoneNumber == null ? "null" : phoneNumber.GetPhoneNumber());
        return res;
    }

    /**
     * Внутренний класс для создания объекта клиента с использованием шаблона проектирования "Builder".
     */
    public static class ClientBuilder {
        private Passport passport;
        private PhoneNumber phoneNumber;
        private String firstName;
        private String secondName;
        private int verificationStatus;

        /**
         * Создает объект класса ClientBuilder со значениями по умолчанию.
         * firstName, secondName, passport, phoneNumber установлены в null,
         * verificationStatus установлен в 0.
         */
        public ClientBuilder() {
            firstName = null;
            secondName = null;
            passport = null;
            phoneNumber = null;
            verificationStatus = 0;
        }

        /**
         * Устанавливает имя клиента.
         *
         * @param newFirstName новое имя клиента
         * @return объект ClientBuilder с установленным именем клиента
         */
        public ClientBuilder addFirstName(String newFirstName) {
            firstName = newFirstName;
            return this;
        }

        /**
         * Устанавливает фамилию клиента.
         *
         * @param newSecondName новая фамилия клиента
         * @return объект ClientBuilder с установленной фамилией клиента
         */
        public ClientBuilder addSecondName(String newSecondName) {
            secondName = newSecondName;
            return this;
        }

        /**
         * Устанавливает паспортные данные клиента.
         *
         * @param newPassport объект Passport с новыми паспортными данными
         * @return объект ClientBuilder с установленными паспортными данными клиента
         */
        public ClientBuilder addPassport(Passport newPassport) {
            passport = (newPassport.GetPassport().isEmpty() ? null : newPassport);

            return this;
        }

        /**
         * Устанавливает номер телефона клиента.
         *
         * @param newPhoneNumber объект PhoneNumber с новым номером телефона
         * @return объект ClientBuilder с установленным номером телефона клиента
         */
        public ClientBuilder addPhoneNumber(PhoneNumber newPhoneNumber) {
            phoneNumber = (newPhoneNumber.GetPhoneNumber().isEmpty() ? null : newPhoneNumber);

            return this;
        }

        /**
         * Создает и возвращает объект класса Client с установленными значениями.
         * Устанавливает verificationStatus в соответствии с наличием паспорта и/или номера телефона.
         *
         * @return объект Client с установленными значениями
         */

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
