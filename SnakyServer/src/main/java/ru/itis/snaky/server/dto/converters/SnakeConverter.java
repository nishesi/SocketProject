package ru.itis.snaky.server.dto.converters;

import ru.itis.snaky.protocol.dto.TransferSnake;
import ru.itis.snaky.server.dto.Cube;
import ru.itis.snaky.server.dto.Snake;

import java.util.List;

public class SnakeConverter {
    public static TransferSnake from(Snake snake) {
        List<Cube> coords = snake.getBodyCoordinates();
        TransferSnake.Cube[] cubes = new TransferSnake.Cube[coords.size()];
        for (int i = 0; i < coords.size(); i++) {
            cubes[i] = new TransferSnake.Cube(coords.get(i).getX(), coords.get(i).getY());
        }
        return new TransferSnake(cubes, snake.getSnakeName(), ColorConverter.from(snake.getColor()), snake.getDirection());
    }
}
