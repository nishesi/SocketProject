package ru.itis.snaky.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.itis.snaky.client.dto.Snake;

import java.util.List;

@RequiredArgsConstructor
public class Drawer {
    @Setter
    private int cubesCount = 10;
    @Setter
    private Color[] backgroundColors = new Color[]{Color.FORESTGREEN, Color.GREEN};
    private final Canvas backgroundCanvas;
    private final Canvas canvas;


    public void paintBackground() {
        GraphicsContext backgroundGraphicsContext = backgroundCanvas.getGraphicsContext2D();

        double cubeHeight = backgroundCanvas.getHeight() / cubesCount;
        double cubeWidth = backgroundCanvas.getWidth() / cubesCount;

        for (int i = 0; i < cubesCount; i++) {
            for (int j = 0; j < cubesCount; j++) {
                backgroundGraphicsContext.setFill(backgroundColors[(i + j) % backgroundColors.length]);
                backgroundGraphicsContext.fillRect(i * cubeWidth, j * cubeHeight, cubeWidth, cubeHeight);
            }
        }
    }

    public void drawSnakes(List<Snake> snakeList) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

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
        double cubeHeight = backgroundCanvas.getHeight() / cubesCount;
        double cubeWidth = backgroundCanvas.getWidth() / cubesCount;
        graphicsContext.fillRect(horizontalIndex * cubeWidth, verticalIndex * cubeHeight, cubeWidth, cubeHeight);
    }

    private void drawHead(int horizontalIndex, int verticalIndex, String nickname, GraphicsContext graphicsContext) {
        drawCube(horizontalIndex, verticalIndex, graphicsContext);
        double cubeHeight = backgroundCanvas.getHeight() / cubesCount;
        double cubeWidth = backgroundCanvas.getWidth() / cubesCount;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(nickname, horizontalIndex * cubeWidth, verticalIndex * cubeHeight);
    }
}
