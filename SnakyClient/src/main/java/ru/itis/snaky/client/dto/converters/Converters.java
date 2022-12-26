package ru.itis.snaky.client.dto.converters;

import javafx.scene.paint.Color;
import ru.itis.snaky.client.dto.Fruit;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.dto.Snake;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.dto.TransferSnake;

import java.util.Arrays;
import java.util.function.Function;

public class Converters {
    public static Room from(TransferRoom transferRoom) {
        return new Room(
                transferRoom.getSize(),
                transferRoom.getName(),
                transferRoom.getPlayers(),
                transferRoom.getCapacity(),
                Arrays.stream(transferRoom.getColorsArray())
                        .map(transferColor -> {
                            short[] rgb = transferColor.getRgb();
                            return Color.rgb(rgb[0], rgb[1], rgb[2]);
                        }).toArray(Color[]::new)
        );
    }

    public static Snake from(TransferSnake transferSnake) {
        int[][] bodies = Arrays.stream(transferSnake.getBodyCubes())
                .map(cube -> new int[]{cube.getX(), cube.getY()})
                .toArray(int[][]::new);

        String name = transferSnake.getName();

        short[] colors = transferSnake.getColor().getRgb();
        Color color = Color.rgb(colors[0], colors[1], colors[2]);

        return new Snake(
                bodies,
                name,
                color
        );
    }

    public static Fruit from(TransferFruit transferFruit) {
        short[] colors = transferFruit.getColor().getRgb();
        Color color = Color.rgb(colors[0], colors[1], colors[2]);

        return new Fruit(
                transferFruit.getX(),
                transferFruit.getY(),
                color
        );
    }

}
