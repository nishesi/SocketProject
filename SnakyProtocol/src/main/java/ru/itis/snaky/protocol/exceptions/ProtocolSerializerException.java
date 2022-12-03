package ru.itis.snaky.protocol.exceptions;

public class ProtocolSerializerException extends RuntimeException {
    public ProtocolSerializerException() {
    }

    public ProtocolSerializerException(String message) {
        super(message);
    }

    public ProtocolSerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolSerializerException(Throwable cause) {
        super(cause);
    }

    public ProtocolSerializerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
