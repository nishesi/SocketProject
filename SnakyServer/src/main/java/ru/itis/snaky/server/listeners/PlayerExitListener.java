package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.CloseParams;
import ru.itis.snaky.server.core.Connection;

import java.util.List;

public class PlayerExitListener extends AbstractServerEventListener {
    public PlayerExitListener() {
        super(MessageType.EXIT.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {

    }
}
