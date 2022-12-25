package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.StartParams;
import ru.itis.snaky.server.core.Connection;

public class PlayerStartGameListener extends AbstractServerEventListener {
    public PlayerStartGameListener() {
        super(MessageType.START.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        connection.getRoom().getCondition().addPlayer(connection);
        connection.getOutputStream().send(new Message<>(MessageType.START, new StartParams(true)));
    }
}
