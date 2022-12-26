package ru.itis.snaky.server.game;

import lombok.Getter;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.dto.TransferSnake;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.RoomConditionParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.core.Server;
import ru.itis.snaky.server.dto.Color;
import ru.itis.snaky.server.dto.Fruit;
import ru.itis.snaky.server.dto.Room;
import ru.itis.snaky.server.dto.Snake;
import ru.itis.snaky.server.dto.converters.FruitConverter;
import ru.itis.snaky.server.dto.converters.SnakeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomCondition extends Thread {

    @Getter
    private List<Snake> snakes;

    private Fruit fruitPosition = new Fruit(5, 2, new Color(255, 255, 0));

    private Room room;

    private Server server;

    public RoomCondition(Server server, Room room) {
        snakes = new ArrayList<>();
        this.room = room;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            updateSnakes();
            sendCondition();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateSnakes() {

        for (Snake snake : snakes) {
            if (snake.getHead()[0] == fruitPosition.getX() && snake.getHead()[1] == fruitPosition.getY()) {
                snake.increase();
            }
            snake.move();
        }
    }

    public void sendCondition() {
        for (Connection connection : this.server.getConnections()) {
            if (connection.getRoom() != null && connection.getRoom().getName().equals(room.getName())) {
                connection.getOutputStream().send(new Message<>(MessageType.ROOM_CONDITION, new RoomConditionParams(snakes.stream().map(SnakeConverter::from).toArray(TransferSnake[]::new), new TransferFruit[]{FruitConverter.from(fruitPosition)})));
            }
        }
    }

    public void addPlayer(Connection connection) {
        List<Integer[]> startCoordinates = new ArrayList<>();
        startCoordinates.add(new Integer[]{2, 0});
        startCoordinates.add(new Integer[]{1, 0});
        startCoordinates.add(new Integer[]{0, 0});
        snakes.add(new Snake(startCoordinates, connection.getPlayerNickname(), new Color(123, 32, 33), "RIGHT"));
    }
}
