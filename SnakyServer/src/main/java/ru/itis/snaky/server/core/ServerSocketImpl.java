package ru.itis.snaky.server.core;

import lombok.Builder;
import ru.itis.snaky.server.dto.Color;
import ru.itis.snaky.server.dto.Room;
import ru.itis.snaky.server.game.RoomCondition;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ServerSocketImpl implements Server {

    private short port;

    private ServerSocket server;

    private List<Connection> connections;

    private Room[] rooms;

    private boolean isStarted;

    @Override
    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);
            connections = new ArrayList<>();
            initRooms();
            isStarted = true;

            while (true) {
                Socket s = server.accept();
                handleConnection(s);
            }
        } catch (IOException e) {
            throw new ServerException("Unable to start server", e);
        }
    }

    public void handleConnection(Socket socket) throws ServerException {
        try {
            Connection connection = new Connection(this, socket);
            connections.add(connection);
            connection.start();
        } catch (IOException e) {
            throw new ServerException("Unable to handle connection", e);
        }
    }

    public void initRooms() {
        rooms = new Room[3];

        rooms[0] = Room.builder()
                .size(20)
                .name("First")
                .playersCount(0)
                .capacity(4)
                .colorsArray(new Color[]{new Color(10, 20, 39), new Color(40, 23, 150)})
                .build();
        rooms[1] = Room.builder()
                .size(20)
                .name("Second")
                .playersCount(0)
                .capacity(4)
                .colorsArray(new Color[]{new Color(10, 20, 39), new Color(40, 23, 150)})
                .build();
        rooms[2] = Room.builder()
                .size(20)
                .name("Third")
                .playersCount(0)
                .capacity(4)
                .colorsArray(new Color[]{new Color(10, 20, 39), new Color(40, 23, 150)})
                .build();

        for (Room room : rooms) {
            room.setCondition(new RoomCondition(this, room));
            room.getCondition().start();
        }
    }

    @Override
    public List<Connection> getConnections() {
        return this.connections;
    }

    @Override
    public void removeConnection(Connection connection) {
        this.connections.remove(connection);
    }

    @Override
    public Room[] getRooms() {
        return this.rooms;
    }
}
