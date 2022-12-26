package ru.itis.snaky.protocol.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRoom {
    /**
     * wight and height of plane
     */
    private int size;

    private String name;

    private int players;

    /**
     * max count of players
     */
    private int capacity;

    /**
     * array of string representations of background shapes colors
     * Preferred length = 2
     */
    private TransferColor[] colorsArray;
}
