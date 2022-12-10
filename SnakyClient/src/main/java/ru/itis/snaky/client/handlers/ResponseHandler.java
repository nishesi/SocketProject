package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.core.InputStreamThread;
import ru.itis.snaky.client.dto.Room;

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

    public ResponseHandler(InputStreamThread inputStreamThread) {
        this.inputStreamThread = inputStreamThread;
        this.rooms = new ArrayList<>(100);
    }

    @Override
    public void run() {
        //TODO read messages perform to assosiate objects
    }

    public List<Room> getRooms() {
        synchronized (rooms) {
            if (rooms.isEmpty()) {
                try {
                    rooms.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            List<Room> toReturn = new ArrayList<>();
            Collections.copy(toReturn, rooms);
            rooms.clear();
            return toReturn;
        }
    }


}
