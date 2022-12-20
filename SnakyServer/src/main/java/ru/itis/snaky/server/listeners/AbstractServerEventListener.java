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
            default:
                throw new ProtocolIllegalMessageTypeException();
        }
    }
}
