package ru.itis.snaky.client.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.dto.Snake;
import ru.itis.snaky.client.gui.canvas.BackgroundResizableCanvas;
import ru.itis.snaky.client.gui.canvas.ForegroundResizableCanvas;

import java.util.List;

public class GameField extends Canvas {
    private final BackgroundResizableCanvas backgroundCanvas;
    private final ForegroundResizableCanvas canvas;
    @Getter
    private final Pane canvasPane;

    public GameField(int cubesCount, Color[] backgroundColors) {
        canvasPane = new Pane();
        backgroundCanvas = new BackgroundResizableCanvas(canvasPane, backgroundColors, cubesCount);
        canvas = new ForegroundResizableCanvas(canvasPane, cubesCount);
    }

    public void drawSnakes(List<Snake> snakeList) {
        snakeList = List.of(
                new Snake(new int[][]{{5, 7}, {6, 7}, {7, 7}}, "snake 1", Color.ALICEBLUE),
                new Snake(new int[][]{{5, 3}, {6, 4}, {7, 5}}, "snake 1", Color.WHITESMOKE),
                new Snake(new int[][]{{1, 1}, {1, 2}, {1, 3}}, "snake 1", Color.PALEGOLDENROD)
        );
        canvas.drawSnakes(snakeList);
    }

}

