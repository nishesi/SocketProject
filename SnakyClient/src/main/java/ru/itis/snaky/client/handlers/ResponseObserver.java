package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.MessageHandler;
import ru.itis.snaky.client.dto.Fruit;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.dto.Snake;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.threads.InputStreamThread;

import java.util.*;

/**
 * all methods for the UI are atomic and can be blocked until the necessary data is received from the server.
 * Methods for message getting is performing in another thread.
 */

public class ResponseObserver extends Thread {
    private final InputStreamThread inputStreamThread;
    private final List<Room> rooms;
    private final List<Snake> snakes;
    private final List<Fruit> fruits;
    private final Map<MessageType, MessageHandler> handlers;
    private boolean isRunning;

    public ResponseObserver(InputStreamThread inputStreamThread) {
        this.inputStreamThread = inputStreamThread;
        this.handlers = new HashMap<>();
        this.rooms = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.fruits = new ArrayList<>();
        isRunning = false;

        initDefaultHandlers();
    }

    private void initDefaultHandlers() {
        handlers.put(MessageType.ROOMS_LIST, message -> {
            synchronized (rooms) {
                rooms.clear();
                List<TransferRoom> transferRooms = (List<TransferRoom>) (message.getParameter(0));

                transferRooms.stream()
                        .map(transferRoom -> new Room(transferRoom.getName(), transferRoom.getPlayers()))
                        .forEach(rooms::add);
            }
        });

        handlers.put(MessageType.ROOM_CONDITION, message -> {
            synchronized (snakes) {
                snakes.clear();
                snakes.addAll((List<Snake>) (message.getParameter(0)));
            }
            synchronized (fruits) {
                fruits.clear();
                fruits.addAll((List<Fruit>) (message.getParameter(1)));
            }
        });
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
            handlers.get(message.getMessageType()).handle(message);

        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new RuntimeException(
                    "Invalid message: message type = " + message.getMessageType().getValue() +
                            " params = " + message.getParameters().toString());
        } catch (NullPointerException ex) {
            throw new RuntimeException(message.getMessageType().name() + " handler not initialized");
        }
    }

    public List<Room> getRooms() {
        synchronized (rooms) {

            return new ArrayList<>(rooms);
        }
    }

    public List<Snake> getSnakes() {
        synchronized (snakes) {
            return new ArrayList<>(snakes);
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

    public void addHandler(MessageType messageType, MessageHandler messageHandler) {
        handlers.put(messageType, messageHandler);
    }
}
