package ru.itis.snaky.protocol.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferSnake {

    private Cube[] bodyCubes;

    private String name;

    private TransferColor color;

    private String direction;

    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Cube {
        private int x;
        private int y;
    }
}
