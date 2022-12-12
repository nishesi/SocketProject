package ru.itis.snaky.server.core;


import ru.itis.snaky.protocol.ProtocolInputStream;
import ru.itis.snaky.protocol.ProtocolOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {

    private long id;

    private Server server;

    private Socket socket;

    private ProtocolInputStream inputStream;
    private ProtocolOutputStream outputStream;

    public Connection(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        inputStream = new ProtocolInputStream(socket.getInputStream());
        outputStream = new ProtocolOutputStream(socket.getOutputStream());
    }


    @Override
    public void run() {
        // todo: process messages and call listeners
    }
}
