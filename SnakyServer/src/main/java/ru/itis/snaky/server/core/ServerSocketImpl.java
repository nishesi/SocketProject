package ru.itis.snaky.server.core;

import lombok.Builder;
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
    private boolean isStarted;

    @Override
    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);
            connections = new ArrayList<>();
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

    @Override
    public List<Connection> getConnections() {
        return this.connections;
    }
}
