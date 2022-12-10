package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.core.OutputStreamThread;

public abstract class ControlHandler extends Thread {
    private final OutputStreamThread outputStreamThread;


    public ControlHandler(OutputStreamThread outputStreamThread) {
        this.outputStreamThread = outputStreamThread;
    }

    public abstract void requestRooms();

}
