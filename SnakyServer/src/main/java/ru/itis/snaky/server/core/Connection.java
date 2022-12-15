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

    @Getter
    private InputStreamThread inputStream;

    @Getter
    private OutputStreamThread outputStream;

    public Connection(Socket socket) throws IOException {
        this.id = UUID.randomUUID();
        inputStream = new InputStreamThread(socket.getInputStream());
        outputStream = new OutputStreamThread(socket.getOutputStream());

        inputStream.start();
        outputStream.start();
    }

    @Override
    public void run() {
        while (true) {
            Message messageFromClient = inputStream.getMessage().orElse(null);
            if (messageFromClient != null) {
                ServerEventListener listener = AbstractServerEventListener.get(messageFromClient.getMessageType());
                listener.handle(this);
            }
        }
    }
}
