package ru.itis.snaky.client.dto;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
