package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.server.core.Connection;

public class PlayerCloseGameListener extends AbstractServerEventListener {
    public PlayerCloseGameListener() {
        super(MessageType.CLOSE.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        connection.close();
    }
}
