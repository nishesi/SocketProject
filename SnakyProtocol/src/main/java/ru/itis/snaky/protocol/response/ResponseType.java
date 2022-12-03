package ru.itis.snaky.protocol.response;

import ru.itis.snaky.protocol.exceptions.ProtocolIllegalTypeException;

public enum ResponseType {
    AUTHORIZATION((byte)0),
    ROOMS_LIST((byte)1),
    SNAKES_POSITIONS((byte) 2),
    LOSING((byte) 3);

    private final byte value;

    ResponseType(byte value) {
        this.value = value;
    }

    public static ResponseType from(String valueString) throws ProtocolIllegalTypeException {
        try {
            byte value = Byte.parseByte(valueString);
            switch (value) {
                case 0:
                    return AUTHORIZATION;
                case 1:
                    return ROOMS_LIST;
                case 2:
                    return SNAKES_POSITIONS;
                case 3:
                    return LOSING;
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
