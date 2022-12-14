package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.dto.Fruit;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.dto.Snake;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.threads.InputStreamThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * all methods for the UI are atomic and can be blocked until the necessary data is received from the server.
 * Methods for message getting is performing in another thread.
 */

public abstract class ResponseHandler extends Thread {
    private final InputStreamThread inputStreamThread;
    private final List<Room> rooms;
    private final List<Snake> snakes;
    private final List<Fruit> fruits;
    private boolean isRunning;

    public ResponseHandler(InputStreamThread inputStreamThread) {
        this.inputStreamThread = inputStreamThread;
        this.rooms = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.fruits = new ArrayList<>();
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            inputStreamThread.getMessage().ifPresent(this::handleMessage);
        }
    }

    private void handleMessage(Message message) {
        try {
            switch (message.getMessageType()) {
                case ROOMS_LIST:
                    synchronized (rooms) {
                        rooms.clear();
                        rooms.addAll((List<Room>) (message.getParameter(0)));
                    }
                    break;
                case SNAKES_POSITIONS:
                    synchronized (snakes) {
                        snakes.clear();
                        snakes.addAll((List<Snake>) (message.getParameter(0)));
                    }
            }
        } catch (ClassCastException ex) {
            throw new RuntimeException(
                    "Invalid message: message type = " + message.getMessageType().getValue() +
                    " params = " + message.getParameters().toString());
        }
    }

    public List<Room> getRooms() {
        synchronized (rooms) {
            return getPropertyCopy(rooms);
        }
    }

    public void finish() {
        isRunning = false;
    }

    public List<Snake> getSnakes() {
        synchronized (snakes) {
            return getPropertyCopy(snakes);
        }
    }

    public List<Fruit> getFruits() {
        synchronized (fruits) {
            return getPropertyCopy(fruits);
        }
    }

    private <T> List<T> getPropertyCopy(List<T> list) {
        List<T> toReturn = new ArrayList<>();
        Collections.copy(toReturn, list);
        return toReturn;
    }
}
