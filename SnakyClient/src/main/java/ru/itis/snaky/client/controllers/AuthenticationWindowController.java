package ru.itis.snaky.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.dto.Room;

import java.util.List;

public class AuthenticationWindowController {
    @Getter
    @Setter
    private AnchorPane authenticationWindow;

    @Setter
    private RoomsWindowController roomsWindowController;

    @FXML
    private TextField nicknameField;

    @FXML
    private Button enterButton;

    @FXML
    void send(ActionEvent event) {
        List<Room> rooms = List.of(new Room("room 1"), new Room("room 2"), new Room("room 3"));
        Stage stage = (Stage) enterButton.getScene().getWindow();
        stage.setScene(new Scene(roomsWindowController.getRoomsWindow()));
        stage.show();
    }

}

