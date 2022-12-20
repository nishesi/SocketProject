package ru.itis.snaky.server.core;

import java.rmi.ServerException;
import java.util.List;

public interface Server {
    void start() throws ServerException;

    List<Connection> getConnections();

}
