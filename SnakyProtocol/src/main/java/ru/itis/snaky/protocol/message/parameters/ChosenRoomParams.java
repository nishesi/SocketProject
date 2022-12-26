package ru.itis.snaky.protocol.message.parameters;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChosenRoomParams extends MessageParams {
    private String chosenRoomName;
    private boolean success;
}
