package ru.itis.snaky.protocol.message;

import ru.itis.snaky.protocol.exceptions.ProtocolIllegalMessageTypeException;

public enum MessageType {
    AUTHORIZATION((byte) 0),
    ROOMS_LIST((byte) 1),
    CHOSEN_ROOM((byte) 2),
    ROOM_CONDITION((byte) 3),
    START((byte) 4),
    DIRECTION((byte) 5),
    LOSING((byte) 6),
    EXIT((byte) 7),
    CLOSE((byte) 8);

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
