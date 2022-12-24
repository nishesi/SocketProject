package ru.itis.snaky.protocol.message.parameters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.itis.snaky.protocol.dto.TransferRoom;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomsListParams extends MessageParams {
    private TransferRoom[] rooms;
}
