package ru.itis.snaky.client.gui.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.client.gui.GameField;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseObserver;
import ru.itis.snaky.protocol.message.MessageType;

import java.io.IOException;

public class GameWindowController {
    private final GameField gameField;
    private final ResponseObserver responseObserver;
    private final ControlHandler controlHandler;

    private EventHandler<? super KeyEvent> keyEventHandler;
    private Timeline animationTimeline;
    @Setter
    private RoomsWindowController roomsWindowController;
    @FXML
    private BorderPane gamePane;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;

    public GameWindowController(RoomsWindowController roomsWindowController, ControlHandler controlHandler,
                                ResponseObserver responseObserver, int cubesCount, Color[] backgroundColors) {
        this.roomsWindowController = roomsWindowController;
        this.controlHandler = controlHandler;
        this.responseObserver = responseObserver;
        initFxml();
        this.gameField = new GameField(cubesCount, backgroundColors);
        gamePane.setCenter(gameField.getCanvasPane());
        initKeyListener();
        addControlHandlers();
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

    private void initKeyListener() {
        keyEventHandler = keyEvent -> {

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
        };
    }

    private void addControlHandlers() {
        responseObserver.addHandler(MessageType.START, message -> {
            gamePane.getScene().setOnKeyPressed(keyEventHandler);
        });

        responseObserver.addHandler(MessageType.LOSING, message -> {
            gamePane.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        });
    }

    private void initGameEnvironment() {

        animationTimeline = new Timeline(new KeyFrame(
                new Duration(1),
                actionEvent -> gameField.drawSnakes(responseObserver.getSnakes())));

        animationTimeline.setCycleCount(Animation.INDEFINITE);
    }

    public void startAnimation() {
        animationTimeline.play();
    }

    public Pane getGamePane() {
        return gamePane;
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
