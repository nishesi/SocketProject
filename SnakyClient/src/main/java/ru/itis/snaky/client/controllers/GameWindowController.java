package ru.itis.snaky.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class GameWindowController {
    @Setter
    private RoomsWindowController roomsWindowController;
    @FXML
    private final Pane gamePane;
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

    public void toRooms() {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.setScene(roomsWindowController.getRoomsPane().getScene());
    }

    public Pane getGamePane() {
        return gamePane;
    }
}
