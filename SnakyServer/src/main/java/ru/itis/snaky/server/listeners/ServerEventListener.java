package ru.itis.snaky.server.listeners;

import ru.itis.snaky.server.core.Connection;

public interface ServerEventListener {
    void handle(Connection connection);
}
