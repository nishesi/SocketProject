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
import java.util.Random;

public class RoomCondition extends Thread {

    @Getter
    private List<Snake> snakes;

    private Fruit fruit;

    private Room room;

    private Server server;

    public RoomCondition(Server server, Room room) {
        snakes = new ArrayList<>();
        this.room = room;
        this.server = server;
    }

    @Override
    public void run() {
        regenerateFruit();
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
            if (snake.getHead()[0] == fruit.getX() && snake.getHead()[1] == fruit.getY()) {
                snake.increase();
                regenerateFruit();
            }
            snake.move();
        }
    }

    public void sendCondition() {
        for (Connection connection : this.server.getConnections()) {
            if (connection.getRoom() != null && connection.getRoom().getName().equals(room.getName())) {
                connection.getOutputStream().send(new Message<>(MessageType.ROOM_CONDITION, new RoomConditionParams(snakes.stream().map(SnakeConverter::from).toArray(TransferSnake[]::new), new TransferFruit[]{FruitConverter.from(fruit)})));
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

    public void regenerateFruit() {
        List<Integer[]> allCubes = new ArrayList<>();
        for (int i = 0; i < room.getSize(); i++) {
            for (int j = 0; j < room.getSize(); j++) {
                allCubes.add(new Integer[]{i, j});
            }
        }

        for (Snake snake : snakes) {
            for (Integer[] pos : snake.getBodyCoordinates()) {
                allCubes.remove(pos);
            }
        }

        Random random = new Random();
        int index = random.nextInt(allCubes.size());

        fruit = new Fruit(allCubes.get(index)[0], allCubes.get(index)[1], new Color(0, 255, 255));
    }
}
