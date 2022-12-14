package ru.itis.snaky.client;

public enum Direction {
    TOP((byte)1),
    RIGHT((byte) 2),
    BOTTOM((byte) 3),
    LEFT((byte) 4),;

    private final byte code;

    Direction(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
