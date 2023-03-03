package ru.aslantemirkanov.banks.entities.notifications;

import ru.aslantemirkanov.banks.entities.Client;

public interface Notification
{
    void send(Client client, String message);
}