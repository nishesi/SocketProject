package ru.itis.snaky.client.dto;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Fruit {
    private int x;
    private int y;
    private Color color;
}
