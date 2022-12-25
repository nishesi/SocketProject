package ru.itis.snaky.client.handlers;

import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.Message;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.*;
import ru.itis.snaky.protocol.threads.OutputStreamThread;

public class ControlHandler {
    private final OutputStreamThread outputStreamThread;

    public ControlHandler(OutputStreamThread outputStreamThread) {
        this.outputStreamThread = outputStreamThread;
    }

    public void sendAuthMessage(String nickname) {
        outputStreamThread.send(new Message<>(
                MessageType.AUTHORIZATION,
                new AuthenticationParams(nickname, false)
        ));
    }

    public void requestRooms() {
        outputStreamThread.send(new Message<>(
                MessageType.ROOMS_LIST,
                new RoomsListParams(new TransferRoom[0])
        ));
    }

    public void sendChosenRoom(Room room) {
        outputStreamThread.send(new Message<>(
                MessageType.CHOSEN_ROOM,
                new ChosenRoomParams(room.getName(), false)
        ));
    }

    public void sendStartGameMessage() {
        outputStreamThread.send(new Message<>(
                MessageType.START,
                new StartParams(false)
        ));
    }

    public void sendDirection(Direction direction) {
        outputStreamThread.send(new Message<>(
                MessageType.DIRECTION,
                new DirectionParams(direction.getCode())
        ));
    }

    public void leaveRoom() {
        outputStreamThread.send(new Message<>(
                MessageType.EXIT,
                new ExitParams()
        ));
    }

    public void sendCloseMessage() {
        outputStreamThread.send(new Message<>(
                MessageType.CLOSE,
                new CloseParams()
        ));
    }
}
