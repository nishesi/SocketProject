package ru.itis.snaky.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameWindowController {
    @FXML
    private AnchorPane anchorPane;
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
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
