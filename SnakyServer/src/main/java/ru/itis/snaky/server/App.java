package ru.itis.snaky.server;

import ru.itis.snaky.server.core.Server;
import ru.itis.snaky.server.core.ServerSocketImpl;

import java.rmi.ServerException;

public class App {
    public static void main(String[] args) {
        Server server = ServerSocketImpl
                .builder()
                .port((short) 7777)
                .build();
        try {
            server.start();
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
