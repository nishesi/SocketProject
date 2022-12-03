package ru.itis.snaky.protocol.exceptions;

public class ProtocolIllegalMessageTypeException extends RuntimeException {

    public ProtocolIllegalMessageTypeException() {
    }

    public ProtocolIllegalMessageTypeException(String message) {
        super(message);
    }

    public ProtocolIllegalMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolIllegalMessageTypeException(Throwable cause) {
        super(cause);
    }

    public ProtocolIllegalMessageTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
