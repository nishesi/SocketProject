package ru.itis.snaky.protocol.message.parameters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.itis.snaky.protocol.dto.TransferRoom;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomsListParams extends MessageParams {
    private TransferRoom[] transferRooms;

    public RoomsListParams(List<TransferRoom> transferRooms) {
        this.transferRooms = transferRooms.toArray(TransferRoom[]::new);
    }
}
