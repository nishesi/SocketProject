package ru.itis.snaky.protocol.message;

import ru.itis.snaky.protocol.exceptions.ProtocolIllegalMessageTypeException;

public enum MessageType {
    AUTHORIZATION((byte) 0),
    ROOMS_LIST((byte) 1),
    CHOOSE_ROOM((byte) 2),
    GAME_JOIN((byte) 3),
    TURNING((byte) 4),
    EXIT((byte) 5),
    SNAKES_POSITIONS((byte) 6),
    LOSING((byte) 7),
    PING((byte) 8),
    PONG((byte)9);

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public static MessageType fromByte(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new ProtocolIllegalMessageTypeException("invalid type = " + value);
    }

    public byte getValue() {
        return value;
    }
}
