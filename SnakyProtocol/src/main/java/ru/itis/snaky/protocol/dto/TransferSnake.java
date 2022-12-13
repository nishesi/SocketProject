package ru.itis.snaky.protocol.dto;

import lombok.*;
import ru.itis.snaky.protocol.SnakySerializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferSnake implements SnakySerializable {

    private Cube[] bodyCubes;

    private String name;

    private String color;

    @Getter
    @RequiredArgsConstructor
    public static class Cube {
        private final int x;
        private final int y;
    }
}
