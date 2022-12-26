package ru.itis.snaky.server.game;

import lombok.Getter;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.dto.TransferSnake;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.LosingParams;
import ru.itis.snaky.protocol.message.parameters.RoomConditionParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.core.Server;
import ru.itis.snaky.server.dto.*;
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
            List<Snake> toRemove = new ArrayList<>();
            for (Snake snake : snakes) {
                if (checkForLosing(snake)) {
                    toRemove.add(snake);
                    sendLosingMessage(snake);
                }
            }
            snakes.removeAll(toRemove);
            updateSnakes();
            sendCondition();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateSnakes() {

        for (Snake snake : snakes) {
            if (snake.getHead().getX() == fruit.getX() && snake.getHead().getY() == fruit.getY()) {
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
        List<Cube> startCoordinates = new ArrayList<>();
        startCoordinates.add(new Cube(2, 0));
        startCoordinates.add(new Cube(1, 0));
        startCoordinates.add(new Cube(0, 0));
        snakes.add(new Snake(startCoordinates, connection.getPlayerNickname(), new Color(123, 32, 33), "RIGHT"));
    }

    public void regenerateFruit() {
        List<Cube> allCubes = new ArrayList<>();
        for (int i = 0; i < room.getSize(); i++) {
            for (int j = 0; j < room.getSize(); j++) {
                allCubes.add(new Cube(i, j));
            }
        }

        for (Snake snake : snakes) {
            for (Cube cube : snake.getBodyCoordinates()) {
                allCubes.remove(cube);
            }
        }
        System.out.println(allCubes.size());

        Random random = new Random();
        int index = random.nextInt(allCubes.size());

        fruit = new Fruit(allCubes.get(index).getX(), allCubes.get(index).getY(), new Color(0, 255, 255));
    }

    public boolean checkForLosing(Snake snake) {
        Cube head = snake.getHead();

        if (head.getX() < 0 || head.getX() >= room.getSize() || head.getY() < 0 || head.getY() >= room.getSize()) {
            return true;
        }

        for (int i = 1; i < snake.getBodyCoordinates().size(); i++) {
            if (head.getX() == snake.getBodyCoordinates().get(i).getX() && head.getY() == snake.getBodyCoordinates().get(i).getY()) {
                return true;
            }
        }

        for (Snake otherSnake : snakes) {
            if (!otherSnake.equals(snake)) {
                for (Cube cube : otherSnake.getBodyCoordinates()) {
                    if (snake.getHead().getX() == cube.getX() && snake.getHead().getY() == cube.getY()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void sendLosingMessage(Snake snake) {
        for (Connection player : server.getConnections()) {
            if (snake.getSnakeName().equals(player.getPlayerNickname())) {
                player.getOutputStream().send(new Message<>(MessageType.LOSING, new LosingParams()));
                break;
            }
        }
    }
}
