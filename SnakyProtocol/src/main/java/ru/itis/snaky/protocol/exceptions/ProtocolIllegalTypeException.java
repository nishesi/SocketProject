package ru.itis.snaky.protocol.exceptions;

public class ProtocolIllegalTypeException extends RuntimeException {

    public ProtocolIllegalTypeException() {
    }

    public ProtocolIllegalTypeException(String message) {
        super(message);
    }

    public ProtocolIllegalTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolIllegalTypeException(Throwable cause) {
        super(cause);
    }

    public ProtocolIllegalTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
