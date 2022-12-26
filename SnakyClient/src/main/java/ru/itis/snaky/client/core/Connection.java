package ru.itis.snaky.client.core;

import lombok.Getter;
import ru.itis.snaky.protocol.threads.InputStreamThread;
import ru.itis.snaky.protocol.threads.OutputStreamThread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * responsible for start and end session with the Server
 */

public class Connection extends Thread {
    @Getter
    private final InputStreamThread inputStreamThread;
    @Getter
    private final OutputStreamThread outputStreamThread;
    private final Socket socket;

    public Connection(InetAddress inetAddress, short port) {
        try {
            this.socket = new Socket(inetAddress, port);
            this.inputStreamThread = new InputStreamThread(socket.getInputStream());
            this.outputStreamThread = new OutputStreamThread(socket.getOutputStream());

            this.inputStreamThread.start();
            this.outputStreamThread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        inputStreamThread.finish();
        outputStreamThread.finish();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
