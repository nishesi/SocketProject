package ru.itis.snaky.protocol.exceptions;

public class IOThreadException extends RuntimeException {
    public IOThreadException(String message) {
        super(message);
    }

    public IOThreadException(String message, Throwable cause) {
        super(message, cause);
    }
}
