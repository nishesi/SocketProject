package ru.itis.snaky.server.core;

import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.server.dto.Room;

import java.rmi.ServerException;
import java.util.List;

public interface Server {
    void start() throws ServerException;

    List<Connection> getConnections();

    void removeConnection(Connection connection);

    Room[] getRooms();

}
