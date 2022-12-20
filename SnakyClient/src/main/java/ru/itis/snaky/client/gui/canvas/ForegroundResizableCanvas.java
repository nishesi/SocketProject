package ru.itis.snaky.client.gui.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.itis.snaky.client.dto.Snake;

import java.util.List;

public class ForegroundResizableCanvas extends ResizableCanvas {
    private List<Snake> snakeList;
    private final int cubesCount;
    public ForegroundResizableCanvas(Pane container, int cubesCount) {
        super(container);
        this.cubesCount = cubesCount;
    }

    public void drawSnakes(List<Snake> snakeList) {
        this.snakeList = snakeList;
        repaint();
    }

    @Override
    protected void repaint() {
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());

        drawSnakes(graphicsContext);
    }

    private void drawSnakes(GraphicsContext graphicsContext) {
        if (snakeList == null || snakeList.size() < 1) {
            return;
        }
        for (Snake snake : snakeList) {
            graphicsContext.setFill(snake.getColor());
            int[][] cubes = snake.getBodyCoordinates();

            drawHead(cubes[0][0], cubes[0][1], snake.getSnakeName(), graphicsContext);
            graphicsContext.setFill(snake.getColor());
            for (int i = 1; i < cubes.length; i++) {
                drawCube(cubes[i][0], cubes[i][1], graphicsContext);
            }
        }
    }

    private void drawCube(int horizontalIndex, int verticalIndex, GraphicsContext graphicsContext) {
        double cubeHeight = getHeight() / cubesCount;
        double cubeWidth = getWidth() / cubesCount;
        graphicsContext.fillRect(horizontalIndex * cubeWidth, verticalIndex * cubeHeight, cubeWidth, cubeHeight);
    }

    private void drawHead(int horizontalIndex, int verticalIndex, String nickname, GraphicsContext graphicsContext) {
        drawCube(horizontalIndex, verticalIndex, graphicsContext);
        double cubeHeight = getHeight() / cubesCount;
        double cubeWidth = getWidth() / cubesCount;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(nickname, horizontalIndex * cubeWidth, verticalIndex * cubeHeight);
    }
}
