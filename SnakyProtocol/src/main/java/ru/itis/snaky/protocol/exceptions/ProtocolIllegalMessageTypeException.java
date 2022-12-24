package ru.itis.snaky.protocol.exceptions;

public class ProtocolIllegalMessageTypeException extends RuntimeException {

    public ProtocolIllegalMessageTypeException(String message) {
        super(message);
    }

    public ProtocolIllegalMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
