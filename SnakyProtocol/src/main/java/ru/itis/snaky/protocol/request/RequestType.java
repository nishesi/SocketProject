package ru.itis.snaky.protocol.request;

import ru.itis.snaky.protocol.exceptions.ProtocolIllegalTypeException;

public enum RequestType {
    AUTHORIZATION((byte) 0),
    CHOOSE_ROOM((byte) 1),
    GAME_JOIN((byte) 2),
    TURNING((byte) 3),
    EXIT((byte) 4);

    private final byte value;

    RequestType(byte value) {
        this.value = value;
    }

    public static RequestType from(String valueString) throws ProtocolIllegalTypeException {
        try {
            byte value = Byte.parseByte(valueString);
            switch (value) {
                case 0:
                    return AUTHORIZATION;
                case 1:
                    return CHOOSE_ROOM;
                case 2:
                    return GAME_JOIN;
                case 3:
                    return TURNING;
                case 4:
                    return EXIT;
            }
        } catch (NullPointerException | NumberFormatException ignored) {
        }

        throw new ProtocolIllegalTypeException("invalid type = " + valueString);
    }

    @Override
    public String toString() {
        return value + "";
    }
}
