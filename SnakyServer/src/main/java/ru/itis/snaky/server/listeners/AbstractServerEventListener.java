package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.exceptions.ProtocolIllegalMessageTypeException;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.server.core.Server;

public abstract class AbstractServerEventListener implements ServerEventListener {
    private byte value;

    protected Server server;

    public AbstractServerEventListener(byte value) {
        this.value = value;
    }

    @Override
    public void init(Server server) {
        this.server = server;
    }

    public static ServerEventListener get(MessageType messageType) {
        switch (messageType) {
            case AUTHORIZATION:
                return new PlayerAuthorizationListener();
            case ROOMS_LIST:
                return new PlayerRequestRoomsListener();
            case CHOSEN_ROOM:
                return new PlayerChosenRoomListener();
            case START:
                return new PlayerStartGameListener();
            case DIRECTION:
                return new PlayerChangeDirectionListener();
            case EXIT:
                return new PlayerExitListener();
            case CLOSE:
                return new PlayerCloseGameListener();
            default:
                throw new ProtocolIllegalMessageTypeException("Illegal message type");
        }
    }
}
