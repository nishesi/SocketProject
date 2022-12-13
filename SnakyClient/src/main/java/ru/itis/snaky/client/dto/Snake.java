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
public class Snake {

    // like a {{x1, y1}, {x2, y2}, ...}

    private int[][] bodyCoordinates;
    private String snakeName;
    private Color color;
}
