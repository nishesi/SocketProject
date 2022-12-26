package ru.itis.snaky.client.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Getter;
import ru.itis.snaky.client.dto.Fruit;
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
        canvas.drawSnakes(snakeList);
    }

    public void drawFruits(List<Fruit> fruits) {
        canvas.drawFruits(fruits);
    }
}

