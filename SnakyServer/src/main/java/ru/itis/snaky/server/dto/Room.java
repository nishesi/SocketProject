package ru.itis.snaky.server.dto;

import lombok.*;
import ru.itis.snaky.server.game.RoomCondition;

@Getter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private int size;
    private String name;
    private int playersCount;
    private int capacity;
    private Color[] colorsArray;
    @Setter
    private RoomCondition condition;
}
