package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.CloseParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.dto.Room;
import ru.itis.snaky.server.dto.Snake;

import java.util.List;

public class PlayerExitListener extends AbstractServerEventListener {
    public PlayerExitListener() {
        super(MessageType.EXIT.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        Room room = connection.getRoom();
        for (Snake snake : room.getCondition().getSnakes()) {
            if (snake.getSnakeName().equals(connection.getPlayerNickname())) {
                room.getCondition().getSnakes().remove(snake);
                break;
            }
        }
        room.setPlayersCount(room.getPlayersCount() - 1);
    }
}
