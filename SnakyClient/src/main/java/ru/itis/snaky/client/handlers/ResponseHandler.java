package ru.itis.snaky.client.handlers;

import javafx.scene.paint.Color;
import ru.itis.snaky.client.core.InputStreamThread;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.dto.Snake;

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
                    rooms.wait(1000);
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
    private Snake snake = new Snake(new int[][]{{4, 11}, {3, 11}, {2, 11}, {1, 11}}, "totot", Color.CYAN);
    private long time = System.currentTimeMillis();
    public List<Snake> getSnakes() {
        if (System.currentTimeMillis() - time > 100) {
            int[][] cubes = snake.getBodyCoordinates();

            for (int[] coordinates : cubes) {
                coordinates[0] += 1;
            }
            time = System.currentTimeMillis();
        }
        return List.of(snake);
    }
}
