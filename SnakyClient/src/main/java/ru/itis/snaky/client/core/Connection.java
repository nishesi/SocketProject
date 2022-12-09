package ru.itis.snaky.client.core;

import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * responsible for start and end session with the Server
 */

public class Connection extends Thread {
    @Getter
    private final ResponseHandler responseHandler;
    private final InputStreamThread inputStreamThread;
    private final OutputStreamThread outputStreamThread;
    @Getter
    private final ControlService controlService;
    private final Socket socket;

    public Connection(InetAddress inetAddress, short port) {
        try {
            this.socket = new Socket(inetAddress, port);
            this.responseHandler = new ResponseHandler();
            this.inputStreamThread = new InputStreamThread(socket.getInputStream(), responseHandler);

            this.outputStreamThread = new OutputStreamThread(socket.getOutputStream());
            this.controlService = new ControlService(outputStreamThread);

            this.inputStreamThread.start();
            this.outputStreamThread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
