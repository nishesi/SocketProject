package ru.itis.snaky.server.core;

import lombok.Getter;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.threads.InputStreamThread;
import ru.itis.snaky.protocol.threads.OutputStreamThread;
import ru.itis.snaky.server.listeners.AbstractServerEventListener;
import ru.itis.snaky.server.listeners.ServerEventListener;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class Connection extends Thread {

    private UUID id;

    private String playerNickname;

    private Server server;

    private Socket socket;

    @Getter
    private InputStreamThread inputStream;

    @Getter
    private OutputStreamThread outputStream;

    public Connection(Server server, Socket socket) throws IOException {
        this.id = UUID.randomUUID();
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
            ServerEventListener listener = AbstractServerEventListener.get(message.getMessageType());
            listener.init(this.server);
            listener.handle(this, message);
        }
    }

    public String getPlayerNickname() {
        return this.playerNickname;
    }

    public void setPlayerNickname(String nickname) {
        this.playerNickname = nickname;
    }

    public void close() {
        this.inputStream.finish();
        this.outputStream.finish();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
