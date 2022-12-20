package ru.itis.snaky.client.gui.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.itis.snaky.client.gui.GameField;

public class BackgroundResizableCanvas extends ResizableCanvas {
    private final Color[] backgroundColors;
    private final int cubesCount;

    public BackgroundResizableCanvas(Pane container, Color[] backgroundColors, int cubesCount) {
        super(container);
        this.backgroundColors = backgroundColors;
        this.cubesCount = cubesCount;
        repaint();
    }

    @Override
    protected void repaint() {
        GraphicsContext backgroundGraphicsContext = getGraphicsContext2D();

        double cubeHeight = getHeight() / cubesCount;
        double cubeWidth = getWidth() / cubesCount;

        for (int i = 0; i < cubesCount; i++) {
            for (int j = 0; j < cubesCount; j++) {
                backgroundGraphicsContext.setFill(backgroundColors[(i + j) % backgroundColors.length]);
                backgroundGraphicsContext.fillRect(i * cubeWidth, j * cubeHeight, cubeWidth, cubeHeight);
            }
        }
    }
}
