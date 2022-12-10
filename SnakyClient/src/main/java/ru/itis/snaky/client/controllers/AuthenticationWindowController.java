package ru.itis.snaky.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseHandler;

public class AuthenticationWindowController {
    @Setter
    private ControlHandler controlHandler;
    @Setter
    private ResponseHandler responseHandler;

    @Getter
    @Setter
    private Pane authenticationPane;

    @Setter
    private RoomsWindowController roomsWindowController;

    @FXML
    private TextField nicknameField;

    @FXML
    private Button enterButton;

    @FXML
    void send() {
//        List<Room> rooms = List.of(new Room("room 1", 10),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 2", 15),
//                new Room("room 3", 5));
//
//        roomsWindowController.setRoomList(rooms);
        controlHandler.requestRooms();
        Stage stage = (Stage) enterButton.getScene().getWindow();
        stage.setScene(new Scene(roomsWindowController.getRoomsPane()));
    }

}

