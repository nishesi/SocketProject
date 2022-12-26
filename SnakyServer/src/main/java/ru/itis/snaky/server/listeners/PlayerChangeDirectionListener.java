package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.DirectionParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.dto.Room;
import ru.itis.snaky.server.dto.Snake;

public class PlayerChangeDirectionListener extends AbstractServerEventListener {
    public PlayerChangeDirectionListener() {
        super(MessageType.DIRECTION.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        Room room = connection.getRoom();

        Snake playerSnake = null;
        for (Snake snake : room.getCondition().getSnakes()) {
            if (snake.getSnakeName().equals(connection.getPlayerNickname())) {
                playerSnake = snake;
            }
        }

        if (playerSnake != null) {
            DirectionParams params = (DirectionParams) message.getParams();
            switch (params.getDirection()) {
                case 1:
                    if (!playerSnake.getDirection().equals("DOWN")) {
                        playerSnake.setDirection("UP");
                    }
                    break;
                case 2:
                    if (!playerSnake.getDirection().equals("LEFT")) {
                        playerSnake.setDirection("RIGHT");
                    }
                    break;
                case 3:
                    if (!playerSnake.getDirection().equals("UP")) {
                        playerSnake.setDirection("DOWN");
                    }
                    break;
                case 4:
                    if (!playerSnake.getDirection().equals("RIGHT")) {
                        playerSnake.setDirection("LEFT");
                    }
                    break;
            }
        }
    }
}
