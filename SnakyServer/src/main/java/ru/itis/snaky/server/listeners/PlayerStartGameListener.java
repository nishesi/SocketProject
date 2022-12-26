package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.StartParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.dto.Room;
import ru.itis.snaky.server.dto.Snake;

public class PlayerStartGameListener extends AbstractServerEventListener {
    public PlayerStartGameListener() {
        super(MessageType.START.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        Room room = connection.getRoom();
        for (Snake snake : room.getCondition().getSnakes()) {
            if (snake.getSnakeName().equals(connection.getPlayerNickname())) {
                return;
            }
        }
        room.getCondition().addPlayer(connection);
        connection.getOutputStream().send(new Message<>(MessageType.START, new StartParams(true)));
    }
}
