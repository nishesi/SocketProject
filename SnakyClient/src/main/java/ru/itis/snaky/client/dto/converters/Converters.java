package ru.itis.snaky.client.dto.converters;

import javafx.scene.paint.Color;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.protocol.dto.TransferRoom;

import java.util.Arrays;

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
}
