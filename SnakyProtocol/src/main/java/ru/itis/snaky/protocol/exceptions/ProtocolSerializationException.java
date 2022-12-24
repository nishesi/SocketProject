package ru.itis.snaky.protocol.exceptions;

public class ProtocolSerializationException extends RuntimeException {

    public ProtocolSerializationException(String message) {
        super(message);
    }

    public ProtocolSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
