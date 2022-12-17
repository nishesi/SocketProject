package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.threads.OutputStreamThread;

import java.util.LinkedList;
import java.util.Queue;

public class ControlHandler {
    private final OutputStreamThread outputStreamThread;

    public ControlHandler(OutputStreamThread outputStreamThread) {
        this.outputStreamThread = outputStreamThread;
    }

    public void requestRooms() {
        outputStreamThread.send(new Message(MessageType.ROOMS_LIST, new Object[0]));
    }

    public void sendInitMessage(String nickname) {
        outputStreamThread.send(new Message(MessageType.AUTHORIZATION, new Object[]{nickname}));
    }

    public void sendChosenRoom(Room room) {
        outputStreamThread.send(new Message(MessageType.CHOOSE_ROOM, new Object[]{room.getName()}));
    }

    public void sendStartGameMessage() {
//        outputStreamThread.send(new Message());
    }

    public void sendDirection(Direction direction) {
        // TODO
    }

    public void sendCloseMessage() {
        // TODO Add session close message type
    }
}
