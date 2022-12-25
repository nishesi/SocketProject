package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.ChosenRoomParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.dto.Room;

public class PlayerChosenRoomListener extends AbstractServerEventListener {
    public PlayerChosenRoomListener() {
        super(MessageType.CHOSEN_ROOM.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        ChosenRoomParams params = (ChosenRoomParams) message.getParams();
        for (Room room : this.server.getRooms()) {
            if (params.getChosenRoomName().equals(room.getName())) {
                if (room.getPlayersCount() == room.getCapacity()) {
                    connection.getOutputStream().send(new Message<>(MessageType.CHOSEN_ROOM, new ChosenRoomParams(params.getChosenRoomName(), false)));
                } else {
                    connection.setRoom(room);
                    room.setPlayersCount(room.getPlayersCount() + 1);
                    connection.getOutputStream().send(new Message<>(MessageType.CHOSEN_ROOM, new ChosenRoomParams(params.getChosenRoomName(), true)));
                }
            }
        }
    }
}
