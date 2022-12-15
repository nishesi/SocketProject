package ru.itis.snaky.server.listeners;

import ru.itis.snaky.protocol.message.MessageType;

public abstract class AbstractServerEventListener implements ServerEventListener {
    private byte value;

    public AbstractServerEventListener(byte value) {
        this.value = value;
    }

    public static ServerEventListener get(MessageType messageType) {
        switch (messageType) {
            // todo
        }
        return null;
    }
}
