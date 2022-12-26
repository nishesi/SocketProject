package ru.itis.snaky.protocol.message.parameters;

import lombok.*;
import ru.itis.snaky.protocol.dto.TransferRoom;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomsListParams extends MessageParams {
    private TransferRoom[] transferRooms;

    public RoomsListParams(List<TransferRoom> transferRooms) {
        this.transferRooms = transferRooms.toArray(TransferRoom[]::new);
    }
}
