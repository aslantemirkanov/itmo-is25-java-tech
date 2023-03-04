package ru.aslantemirkanov.banks.entities.notifications;

import ru.aslantemirkanov.banks.entities.Client;

/**
 * Интерфейс Notification представляет собой оповещение, которое может быть отправлено клиенту.
 */
public interface Notification {

    /**
     * Отправляет оповещение клиенту.
     *
     * @param client  Клиент, которому необходимо отправить оповещение.
     * @param message Сообщение, которое будет отправлено клиенту.
     */
    void send(Client client, String message);
}