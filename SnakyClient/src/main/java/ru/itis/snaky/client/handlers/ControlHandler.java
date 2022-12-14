package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.threads.OutputStreamThread;

public class ControlHandler extends Thread {
    private final OutputStreamThread outputStreamThread;


    public ControlHandler(OutputStreamThread outputStreamThread) {
        this.outputStreamThread = outputStreamThread;
    }

    public void requestRooms() {
        // TODO
    }

    public void sendInitMessage(String nickname) {
        outputStreamThread.send(new Message(MessageType.AUTHORIZATION, new Object[]{nickname}));
    }
    public void sendChosenRoom(Room room) {
        // TODO
    }

    public void sendStartGameMessage() {
        // TODO
    }

    public void sendDirection(Direction direction) {
        // TODO
    }

    public void sendCloseMessage() {
        // TODO Add session close message type
    }
}
