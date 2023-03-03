package ru.aslantemirkanov.banks.entities.notifications;

import ru.aslantemirkanov.banks.entities.Client;

public class PhoneNotification implements Notification {
    @Override
    public void send(Client client, String message) {
        client.getNotification("notification by phone: " + message);
    }
}
