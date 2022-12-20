package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.threads.OutputStreamThread;

public class ControlHandler {
    private final OutputStreamThread outputStreamThread;

    public ControlHandler(OutputStreamThread outputStreamThread) {
        this.outputStreamThread = outputStreamThread;
    }

    public void requestRooms() {
        outputStreamThread.send(new Message(MessageType.ROOMS_LIST, new Object[0]));
    }

    public void sendAuthMessage(String nickname) {
        outputStreamThread.send(new Message(MessageType.AUTHORIZATION, new Object[]{nickname}));
    }

    public void sendChosenRoom(Room room) {
        outputStreamThread.send(new Message(MessageType.CHOSEN_ROOM, new Object[]{room.getName()}));
    }

    public void sendStartGameMessage() {
        outputStreamThread.send(new Message(MessageType.START, new Object[0]));
    }

    public void sendDirection(Direction direction) {
        outputStreamThread.send(new Message(MessageType.DIRECTION, new Object[]{direction.getCode()}));
    }

    public void leaveRoom() {
        outputStreamThread.send(new Message(MessageType.EXIT, new Object[0]));
    }

    public void sendCloseMessage() {
        outputStreamThread.send(new Message(MessageType.CLOSE, new Object[0]));
    }
}
