package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.core.Server;

public interface ServerEventListener {
    void init(Server server);

    void handle(Connection connection, Message message);
}
