package ru.itis.snaky.protocol.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor()
public class TransferSnake {

    private Cube[] bodyCubes;

    private String name;

    private TransferColor color;

    @Getter
    @ToString
    @EqualsAndHashCode
    @RequiredArgsConstructor
    public static class Cube {
        private final int x;
        private final int y;
    }
}
