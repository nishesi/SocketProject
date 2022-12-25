package ru.itis.snaky.server.core;

import lombok.Builder;
import ru.itis.snaky.protocol.dto.TransferColor;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.Message;

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

    private TransferRoom[] rooms;

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
        rooms = new TransferRoom[5];
        rooms[0] = new TransferRoom(50, "First", 0, 4, new TransferColor[]{new TransferColor(10, 20, 39)});
        rooms[1] = new TransferRoom(50, "Second", 0, 4, new TransferColor[]{new TransferColor(10, 20, 39)});
        rooms[2] = new TransferRoom(50, "Third", 0, 4, new TransferColor[]{new TransferColor(10, 20, 39)});
        rooms[3] = new TransferRoom(50, "Fourth", 0, 4, new TransferColor[]{new TransferColor(10, 20, 39)});
        rooms[4] = new TransferRoom(50, "Fifth", 0, 4, new TransferColor[]{new TransferColor(10, 20, 39)});
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
    public TransferRoom[] getRooms() {
        return this.rooms;
    }
}
