package ru.itis.snaky.client.core;

import ru.itis.snaky.protocol.Message;
import ru.itis.snaky.protocol.ProtocolOutputStream;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * OutputStream not closed after finishing
 */

public class OutputStreamThread extends Thread {
    private static final long SEND_TIMEOUT = 10;
    private final ProtocolOutputStream protocolOutputStream;
    private final Queue<Message> messageQueue;
    private boolean isRunning;

    public OutputStreamThread(OutputStream outputStream) {
        this.protocolOutputStream = new ProtocolOutputStream(outputStream);
        this.messageQueue = new LinkedList<>();
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (messageQueue) {
                while (!messageQueue.isEmpty()) {
                    protocolOutputStream.writeMessage(messageQueue.poll());
                }
            }
            try {
                Thread.sleep(SEND_TIMEOUT);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void send(Message message) {
        if (isRunning) {
            throw new RuntimeException("Thread finished");
        }
        synchronized (messageQueue) {
            messageQueue.add(message);
        }
    }

    public void finish() {
        isRunning = false;
    }
}
