package ru.itis.snaky.protocol;

import ru.itis.snaky.protocol.exceptions.ProtocolIllegalMessageTypeException;

public enum MessageType {
    AUTHORIZATION((byte) 0),
    CHOOSE_ROOM((byte) 1),
    ROOMS_LIST((byte) 5),
    GAME_JOIN((byte) 2),
    TURNING((byte) 3),
    EXIT((byte) 4),
    SNAKES_POSITIONS((byte) 6),
    LOSING((byte) 7);

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
