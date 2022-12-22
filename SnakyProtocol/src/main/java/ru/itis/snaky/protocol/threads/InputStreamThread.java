package ru.itis.snaky.protocol.threads;

import ru.itis.snaky.protocol.exceptions.IOThreadException;
import ru.itis.snaky.protocol.io.ProtocolInputStream;
import ru.itis.snaky.protocol.message.Message;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class InputStreamThread extends Thread {
    private final ProtocolInputStream protocolInputStream;
    private final Queue<Message> messages;
    private boolean isRunning;

    public InputStreamThread(InputStream inputStream) {
        this.protocolInputStream = new ProtocolInputStream(inputStream);
        this.messages = new LinkedList<>();
        setName("Protocol-InputStream-Thread");
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            Message message = protocolInputStream.readMessage();
            synchronized (messages) {
                messages.add(message);
            }
        }
    }

    public Message getMessage() {
        if (this.isAlive()) {
            synchronized (messages) {
                waitMessages();
                return messages.poll();
            }
        }
        throw new IOThreadException("can't read: " + getName() + " finished.");
    }

    private void waitMessages() {
        while (messages.isEmpty()) {
            try {
                messages.wait(10);
            } catch (InterruptedException e) {
                throw new IOThreadException("synchronization exception.", e);
            }
        }
    }

    /**
     * InputStream not closed after finishing
     */
    public void finish() {
        isRunning = false;
    }
}
