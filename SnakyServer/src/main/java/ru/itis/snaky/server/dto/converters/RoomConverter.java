package ru.itis.snaky.server.dto.converters;

import ru.itis.snaky.protocol.dto.TransferColor;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.server.dto.Room;

import java.util.Arrays;

public class RoomConverter {
    public static TransferRoom from(Room room) {
        return new TransferRoom(room.getSize(), room.getName(), room.getPlayersCount(), room.getCapacity(), Arrays.stream(room.getColorsArray()).map(ColorConverter::from).toArray(TransferColor[]::new));
    }
}
