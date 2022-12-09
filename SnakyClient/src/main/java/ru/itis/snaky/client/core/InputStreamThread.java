package ru.itis.snaky.client.core;

import ru.itis.snaky.protocol.Message;
import ru.itis.snaky.protocol.ProtocolInputStream;

import java.io.InputStream;

public class InputStreamThread extends Thread {
    private final ResponseHandler responseHandler;
    private final ProtocolInputStream protocolInputStream;
    private boolean isRunning;

    public InputStreamThread(InputStream inputStream, ResponseHandler responseHandler) {
        this.protocolInputStream = new ProtocolInputStream(inputStream);
        this.responseHandler = responseHandler;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            Message message = protocolInputStream.readMessage();
            responseHandler.handle(message);
        }
    }

    public void finish() {
        isRunning = false;
    }
}
