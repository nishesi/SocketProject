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

    @Getter
    private InputStreamThread inputStream;

    @Getter
    private OutputStreamThread outputStream;

    public Connection(Server server, Socket socket) throws IOException {
        this.id = UUID.randomUUID();
        this.server = server;
        inputStream = new InputStreamThread(socket.getInputStream());
        outputStream = new OutputStreamThread(socket.getOutputStream());

        inputStream.start();
        outputStream.start();
    }

    @Override
    public void run() {
        while (true) {
            Message message = inputStream.getMessage();
            System.out.println(message.getMessageType());
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
}
