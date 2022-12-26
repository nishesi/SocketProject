package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.dto.Fruit;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.dto.Snake;
import ru.itis.snaky.client.dto.converters.Converters;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.MessageParams;
import ru.itis.snaky.protocol.message.parameters.RoomConditionParams;
import ru.itis.snaky.protocol.message.parameters.RoomsListParams;
import ru.itis.snaky.protocol.threads.InputStreamThread;

import java.util.*;

/**
 * all methods for the UI are atomic and can be blocked until the necessary data is received from the server.
 * Methods for message getting is performing in another thread.
 */

public class ResponseObserver extends Thread {
    private final InputStreamThread inputStreamThread;
    private final Map<MessageType, MessageHandler<?>> handlers;
    private boolean isRunning;

    public ResponseObserver(InputStreamThread inputStreamThread) {
        this.inputStreamThread = inputStreamThread;
        this.handlers = new HashMap<>();
        isRunning = false;

        initDefaultHandlers();
    }

    private void initDefaultHandlers() {
        for(MessageType messageType : MessageType.values()) {
            handlers.put(messageType, new MessageHandler<MessageParams>() {
                @Override
                public void handle(MessageParams params) {
                    System.out.println("handler not initialized: " + messageType);
                }
            });
        }
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            handleMessage(inputStreamThread.getMessage());
        }
    }

    private <T extends MessageParams> void handleMessage(Message<T> message) {
        System.out.println(message);
        try {
            MessageHandler<T> handler = (MessageHandler<T>) handlers.get(message.getMessageType());
            handler.handle(message.getParams());

        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new RuntimeException(
                    "Invalid message: message type = " + message.getMessageType().getValue() +
                            " params = " + message.getParams().toString());
        } catch (NullPointerException ex) {
            throw new RuntimeException(message.getMessageType().name() + " handler not initialized");
        }
    }

    public <T extends MessageParams> void addHandler(MessageType messageType, MessageHandler<T> messageHandler) {
        handlers.put(messageType, messageHandler);
    }
}
