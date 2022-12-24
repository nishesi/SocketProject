package ru.itis.snaky.protocol.message.parameters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.itis.snaky.protocol.dto.TransferFruit;
import ru.itis.snaky.protocol.dto.TransferSnake;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomConditionParams extends MessageParams {
    private TransferSnake[] snakes;
    private TransferFruit[] fruits;
}
