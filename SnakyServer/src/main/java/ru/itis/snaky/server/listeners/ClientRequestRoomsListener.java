package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.RoomsListParams;
import ru.itis.snaky.server.core.Connection;

public class ClientRequestRoomsListener extends AbstractServerEventListener {
    public ClientRequestRoomsListener() {
        super(MessageType.ROOMS_LIST.getValue());
    }

    @Override
    public void handle(Connection connection, Message<?> message) {
        connection.getOutputStream().send(new Message<>(MessageType.ROOMS_LIST, new RoomsListParams(this.server.getRooms())));
    }
}
