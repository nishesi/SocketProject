package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.RoomsListParams;
import ru.itis.snaky.server.core.Connection;
import ru.itis.snaky.server.dto.converters.RoomConverter;

import java.util.Arrays;

public class ClientRequestRoomsListener extends AbstractServerEventListener {
    public ClientRequestRoomsListener() {
        super(MessageType.ROOMS_LIST.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        connection.getOutputStream().send(new Message<>(MessageType.ROOMS_LIST, new RoomsListParams(Arrays.stream(this.server.getRooms()).map(RoomConverter::from).toArray(TransferRoom[]::new))));
    }
}
