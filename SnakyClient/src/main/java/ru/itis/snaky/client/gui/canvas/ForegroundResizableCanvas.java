package ru.itis.snaky.client.gui.canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ru.itis.snaky.client.dto.Fruit;
import ru.itis.snaky.client.dto.Snake;

import java.util.List;

public class ForegroundResizableCanvas extends ResizableCanvas {
    private final int cubesCount;
    private List<Snake> snakeList;
    private List<Fruit> fruitList;

    public ForegroundResizableCanvas(Pane container, int cubesCount) {
        super(container);
        this.cubesCount = cubesCount;
    }

    public void drawSnakes(List<Snake> snakes) {
        this.snakeList = snakes;
        repaint();
    }

    public void drawFruits(List<Fruit> fruits) {
        this.fruitList = fruits;
        repaint();
    }

    @Override
    protected void repaint() {
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());

        drawFruits(graphicsContext);
        drawSnakes(graphicsContext);
    }

    private void drawFruits(GraphicsContext graphicsContext) {
        if (fruitList == null) {
            return;
        }
        for (Fruit fruit : fruitList) {
            graphicsContext.setFill(fruit.getColor());
            drawCube(fruit.getX(), fruit.getY(), graphicsContext);
        }
    }

    private void drawSnakes(GraphicsContext graphicsContext) {
        if (snakeList == null || snakeList.size() < 1) {
            return;
        }
        for (Snake snake : snakeList) {
            graphicsContext.setFill(snake.getColor());
            int[][] cubes = snake.getBodyCoordinates();

            graphicsContext.setFill(snake.getColor());
            for (int[] cube : cubes) {
                drawCube(cube[0], cube[1], graphicsContext);
            }
            drawNickname(cubes[0][0], cubes[0][1], snake.getSnakeName(), graphicsContext);
        }
    }

    private void drawCube(int horizontalIndex, int verticalIndex, GraphicsContext graphicsContext) {
        double cubeHeight = getHeight() / cubesCount;
        double cubeWidth = getWidth() / cubesCount;
        graphicsContext.fillRect(horizontalIndex * cubeWidth, verticalIndex * cubeHeight, cubeWidth, cubeHeight);
    }

    private void drawNickname(int horizontalIndex, int verticalIndex, String nickname, GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        double cubeHeight = getHeight() / cubesCount;
        double cubeWidth = getWidth() / cubesCount;
        graphicsContext.fillText(nickname, horizontalIndex * cubeWidth, verticalIndex * cubeHeight);
    }
}
