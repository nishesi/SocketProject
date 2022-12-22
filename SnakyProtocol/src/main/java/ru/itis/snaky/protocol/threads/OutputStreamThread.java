package ru.itis.snaky.protocol.threads;

import ru.itis.snaky.protocol.exceptions.IOThreadException;
import ru.itis.snaky.protocol.io.ProtocolOutputStream;
import ru.itis.snaky.protocol.message.Message;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class OutputStreamThread extends Thread {
    private final ProtocolOutputStream protocolOutputStream;
    private final Queue<Message> messageQueue;
    private boolean isRunning;

    public OutputStreamThread(OutputStream outputStream) {
        this.protocolOutputStream = new ProtocolOutputStream(outputStream);
        this.messageQueue = new LinkedList<>();
        setName("Protocol-OutputStream-Thread");
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            synchronized (messageQueue) {
                waitMessages();
                while (!messageQueue.isEmpty()) {
                    protocolOutputStream.writeMessage(messageQueue.poll());
                }
            }
        }
    }

    private void waitMessages() {
        if (messageQueue.isEmpty()) {
            try {
                messageQueue.wait();
            } catch (InterruptedException e) {
                throw new IOThreadException("synchronization exception.", e);
            }
        }
    }

    public void send(Message message) {
        if (!this.isAlive()) {
            throw new IOThreadException("can't send: " + getName() + " finished.");
        }

        synchronized (messageQueue) {
            messageQueue.add(message);
            messageQueue.notify();
        }
    }

    /**
     * OutputStream not closed after finishing
     */
    public void finish() {
        isRunning = false;
    }
}
