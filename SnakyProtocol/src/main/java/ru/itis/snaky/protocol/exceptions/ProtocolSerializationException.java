package ru.itis.snaky.protocol.exceptions;

public class ProtocolSerializationException extends RuntimeException {
    public ProtocolSerializationException() {
    }

    public ProtocolSerializationException(String message) {
        super(message);
    }

    public ProtocolSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolSerializationException(Throwable cause) {
        super(cause);
    }

    public ProtocolSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
