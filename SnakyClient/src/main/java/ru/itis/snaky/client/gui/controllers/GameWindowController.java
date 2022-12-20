package ru.itis.snaky.client.gui.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.client.gui.Drawer;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseObserver;

import java.io.IOException;

public class GameWindowController {
    private final Drawer drawer;
    private final ResponseObserver responseObserver;
    private final ControlHandler controlHandler;
    @Setter
    private RoomsWindowController roomsWindowController;
    private Timeline animationTimeline;
    @FXML
    private Pane gamePane;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private Canvas backgroundCanvas;
    @FXML
    private Canvas canvas;

    public GameWindowController(RoomsWindowController roomsWindowController, ControlHandler controlHandler, ResponseObserver responseObserver) {
        this.roomsWindowController = roomsWindowController;
        this.controlHandler = controlHandler;
        this.responseObserver = responseObserver;
        initFxml();
        this.drawer = new Drawer(backgroundCanvas, canvas);
        initGameEnvironment();
    }

    private void initFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(GameWindowController.class.getResource("/layout/gameWindow.fxml"));
        fxmlLoader.setController(this);

        try {
            gamePane = fxmlLoader.load();
            gamePane.getStylesheets().add("/css/main.css");
            gamePane.getStylesheets().add("/css/game-window.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Scene(gamePane);
    }

    private void addKeyListener() {
        gamePane.getScene().setOnKeyReleased(keyEvent -> {

            switch (keyEvent.getCode().getCode()) {
                case 38:
                case 87:
                    controlHandler.sendDirection(Direction.TOP);
                    break;
                case 68:
                case 39:
                    controlHandler.sendDirection(Direction.RIGHT);
                    break;
                case 83:
                case 40:
                    controlHandler.sendDirection(Direction.BOTTOM);
                    break;
                case 65:
                case 37:
                    controlHandler.sendDirection(Direction.LEFT);
                    break;
            }
        });
    }

    private void initGameEnvironment() {
        drawer.paintBackground();

        animationTimeline = new Timeline(new KeyFrame(
                new Duration(1),
                actionEvent -> drawer.drawSnakes(responseObserver.getSnakes())));

        animationTimeline.setCycleCount(Animation.INDEFINITE);
    }

    public void startAnimation() {
        animationTimeline.play();
    }

    public Pane getGamePane() {
        return gamePane;
    }

    public void setBackgroundColors(Color[] colors) {
        drawer.setBackgroundColors(colors);
    }

    public void setSize(int size) {
        drawer.setCubesCount(size);
    }

    @FXML
    public void toRooms() {
        controlHandler.requestRooms();
        roomsWindowController.updateRooms();

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setScene(roomsWindowController.getRoomsPane().getScene());
    }

    @FXML
    public void startGame() {
        controlHandler.sendStartGameMessage();
    }
}
