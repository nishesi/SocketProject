package ru.itis.snaky.client.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import ru.itis.snaky.client.dto.Snake;
import ru.itis.snaky.client.handlers.ResponseHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GameWindowController {
    @Setter
    private ResponseHandler responseHandler;
    @Setter
    private int cubesCount = 10;
    @Setter
    private Color[] colors = new Color[]{Color.FORESTGREEN, Color.GREEN};
    @Setter
    private RoomsWindowController roomsWindowController;
    @FXML
    private final Pane gamePane;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private Canvas backgroundCanvas;
    @FXML
    private Canvas canvas;

    public GameWindowController() {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomsWindowController.class.getResource("/layout/GameWindow.fxml"));
        fxmlLoader.setController(this);
        try {
            gamePane = fxmlLoader.load();
            gamePane.getStylesheets().add("/css/main.css");
            gamePane.getStylesheets().add("/css/GameWindow.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GameWindowController(RoomsWindowController roomsWindowController) {
        this();
        this.roomsWindowController = roomsWindowController;
    }

    public void start() {
        paintBackground();

        Timeline timeline = new Timeline(new KeyFrame(
                new Duration(1),
                actionEvent -> {
                    drawSnakes(responseHandler.getSnakes());
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void toRooms() {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.setScene(roomsWindowController.getRoomsPane().getScene());
    }

    public Pane getGamePane() {
        return gamePane;
    }

    private void paintBackground() {
        GraphicsContext backgroundGraphicsContext = backgroundCanvas.getGraphicsContext2D();

        double cubeHeight = backgroundCanvas.getHeight() / cubesCount;
        double cubeWidth = backgroundCanvas.getWidth() / cubesCount;

        for (int i = 0; i < cubesCount; i++) {
            for (int j = 0; j < cubesCount; j++) {
                backgroundGraphicsContext.setFill(colors[(i+j) % colors.length]);
                backgroundGraphicsContext.fillRect(i*cubeWidth, j*cubeHeight, cubeWidth, cubeHeight);
            }
        }
    }

    private void drawSnakes(List<Snake> snakeList) {
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
        graphicsContext.fillRect(horizontalIndex*cubeWidth, verticalIndex*cubeHeight, cubeWidth, cubeHeight);
    }

    private void drawHead(int horizontalIndex, int verticalIndex,String nickname, GraphicsContext graphicsContext) {
        drawCube(horizontalIndex, verticalIndex, graphicsContext);
        double cubeHeight = backgroundCanvas.getHeight() / cubesCount;
        double cubeWidth = backgroundCanvas.getWidth() / cubesCount;
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(nickname, horizontalIndex*cubeWidth, verticalIndex*cubeHeight);
    }
}
