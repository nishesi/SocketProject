package ru.itis.snaky.server.core;

import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.threads.InputStreamThread;
import ru.itis.snaky.protocol.threads.OutputStreamThread;
import ru.itis.snaky.server.dto.Room;
import ru.itis.snaky.server.listeners.AbstractServerEventListener;
import ru.itis.snaky.server.listeners.ServerEventListener;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class Connection extends Thread {

    @Getter
    private UUID uuid;

    @Getter
    @Setter
    private String playerNickname;

    @Getter
    @Setter
    private Room room;

    private Server server;

    private Socket socket;

    @Getter
    private InputStreamThread inputStream;

    @Getter
    private OutputStreamThread outputStream;

    public Connection(Server server, Socket socket) throws IOException {
        this.uuid = UUID.randomUUID();
        this.server = server;
        this.socket = socket;

        inputStream = new InputStreamThread(socket.getInputStream());
        outputStream = new OutputStreamThread(socket.getOutputStream());

        inputStream.start();
        outputStream.start();
    }

    @Override
    public void run() {
        while (true) {
            Message<?> message = inputStream.getMessage();
            System.out.println(message);
            ServerEventListener listener = AbstractServerEventListener.get(message.getMessageType());
            listener.init(this.server);
            listener.handle(this, message);
        }
    }

    public void close() {
        this.inputStream.finish();
        this.outputStream.finish();
        try {
            socket.close();
            server.removeConnection(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
