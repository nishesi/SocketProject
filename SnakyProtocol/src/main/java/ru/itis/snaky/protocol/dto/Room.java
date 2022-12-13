package ru.itis.snaky.protocol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    // wight and height of plane
    private int size;

    private String name;

    private int players;

    //max count of players
    private int capacity;
}
