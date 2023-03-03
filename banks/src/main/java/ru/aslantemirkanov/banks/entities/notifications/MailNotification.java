package ru.aslantemirkanov.banks.entities.notifications;

import ru.aslantemirkanov.banks.entities.Client;

public class MailNotification implements Notification {
    private Notification previousNotificator;

    public MailNotification(Notification previousNotificator) {
        this.previousNotificator = previousNotificator;
    }

    public void send(Client client, String message) {
        client.getNotification("notification by mail: " + message);
        previousNotificator.send(client, message);
    }
}