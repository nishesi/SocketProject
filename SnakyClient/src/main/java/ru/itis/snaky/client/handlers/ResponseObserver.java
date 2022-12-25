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
    private final List<Room> rooms;
    private final List<Snake> snakes;
    private final List<Fruit> fruits;
    private final Map<MessageType, MessageHandler<?>> handlers;
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
        handlers.put(MessageType.ROOMS_LIST, (MessageHandler<RoomsListParams>) params -> {
            synchronized (rooms) {
                rooms.clear();
                TransferRoom[] transferRooms = params.getRooms();

                Arrays.stream(transferRooms)
                        .map(Converters::from)
                        .forEach(rooms::add);
            }
        });

        handlers.put(MessageType.ROOM_CONDITION, (MessageHandler<RoomConditionParams>) message -> {
            synchronized (snakes) {
                snakes.clear();
                //todo addition
//                snakes.addAll((List<Snake>) (message.getParameter(0)));
            }
            synchronized (fruits) {
                fruits.clear();
                //todo addition
//                fruits.addAll((List<Fruit>) (message.getParameter(1)));
            }
        });
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            handleMessage(inputStreamThread.getMessage());
        }
    }

    private <T extends MessageParams> void handleMessage(Message<T> message) {
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

    public <T extends MessageParams> void addHandler(MessageType messageType, MessageHandler<T> messageHandler) {
        handlers.put(messageType, messageHandler);
    }
}
