package ru.itis.snaky.protocol.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itis.snaky.protocol.exceptions.ProtocolIllegalMessageTypeException;
import ru.itis.snaky.protocol.message.parameters.*;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    AUTHORIZATION((byte) 0, AuthMessPar.class),
    ROOMS_LIST((byte) 1, RoomsListMessPar.class),
    CHOSEN_ROOM((byte) 2, ChosenRoomPar.class),
    ROOM_CONDITION((byte) 3, RoomConditionPar.class),
    START((byte) 4, StartPar.class),
    DIRECTION((byte) 5, DirectionPar.class),
    LOSING((byte) 6, LosingPar.class),
    EXIT((byte) 7, ExitPar.class),
    CLOSE((byte) 8, ClosePar.class);

    private final byte value;
    private final Class<? extends MessageParameter> parameterClass;


    public static MessageType fromByte(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new ProtocolIllegalMessageTypeException("invalid type = " + value);
    }
}
