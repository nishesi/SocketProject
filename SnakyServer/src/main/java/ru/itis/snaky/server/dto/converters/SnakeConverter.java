package ru.itis.snaky.server.dto.converters;

import ru.itis.snaky.protocol.dto.TransferSnake;
import ru.itis.snaky.server.dto.Snake;

import java.util.List;

public class SnakeConverter {
    public static TransferSnake from(Snake snake) {
        List<Integer[]> coords = snake.getBodyCoordinates();
        TransferSnake.Cube[] cubes = new TransferSnake.Cube[coords.size()];
        for (int i = 0; i < coords.size(); i++) {
            cubes[i] = new TransferSnake.Cube(coords.get(i)[0], coords.get(i)[1]);
        }
        return new TransferSnake(cubes, snake.getSnakeName(), ColorConverter.from(snake.getColor()), snake.getDirection());
    }
}
