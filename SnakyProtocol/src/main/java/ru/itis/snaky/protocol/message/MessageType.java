package ru.itis.snaky.protocol.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.itis.snaky.protocol.exceptions.ProtocolIllegalMessageTypeException;
import ru.itis.snaky.protocol.message.parameters.*;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    AUTHORIZATION((byte) 0, AuthenticationParams.class),
    ROOMS_LIST((byte) 1, RoomsListParams.class),
    CHOSEN_ROOM((byte) 2, ChosenRoomParams.class),
    ROOM_CONDITION((byte) 3, RoomConditionParams.class),
    START((byte) 4, StartParams.class),
    DIRECTION((byte) 5, DirectionParams.class),
    LOSING((byte) 6, LosingParams.class),
    EXIT((byte) 7, ExitParams.class),
    CLOSE((byte) 8, CloseParams.class);

    private final byte value;
    private final Class<? extends MessageParams> parameterClass;

    public static MessageType fromByte(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new ProtocolIllegalMessageTypeException("invalid type = " + value);
    }
}
