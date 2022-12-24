package ru.itis.snaky.protocol.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
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
