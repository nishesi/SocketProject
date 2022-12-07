package ru.itis.snaky.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameWindowController {
    @FXML
    private final Pane gameWindow;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private Canvas canvas;

    public GameWindowController() {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomsWindowController.class.getResource("/layout/GameWindow.fxml"));
        fxmlLoader.setController(this);
        try {
            gameWindow = fxmlLoader.load();
            gameWindow.getStylesheets().add("/css/main.css");
            gameWindow.getStylesheets().add("/css/GameWindow.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getGameWindow() {
        return gameWindow;
    }
}
