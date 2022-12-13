package ru.itis.snaky.protocol.threads;

import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.io.ProtocolInputStream;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class InputStreamThread extends Thread {
    private final ProtocolInputStream protocolInputStream;
    private final Queue<Message> messages;
    private boolean isRunning;

    public InputStreamThread(InputStream inputStream) {
        this.protocolInputStream = new ProtocolInputStream(inputStream);
        this.messages = new LinkedList<>();
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            Message message = protocolInputStream.readMessage();
            synchronized (messages) {
                messages.add(message);
            }
        }
    }

    public Optional<Message> getMessage() {
        if (!isRunning) {
            throw new RuntimeException("Thread finished");
        }
        synchronized (messages) {
            return Optional.ofNullable(messages.poll());
        }
    }

    public void finish() {
        isRunning = false;
    }
}
