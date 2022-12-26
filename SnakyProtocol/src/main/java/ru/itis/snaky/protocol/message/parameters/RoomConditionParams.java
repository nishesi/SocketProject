package ru.itis.snaky.protocol.message.parameters;

import lombok.*;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.protocol.dto.TransferSnake;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomConditionParams extends MessageParams {
    private TransferSnake[] transferSnakes;
    private TransferFruit[] transferFruits;

    public RoomConditionParams(List<TransferSnake> transferSnakes, List<TransferFruit> transferFruits) {
        this.transferSnakes = transferSnakes.toArray(TransferSnake[]::new);
        this.transferFruits = transferFruits.toArray(TransferFruit[]::new);
    }
}
