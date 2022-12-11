package ru.itis.snaky.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        String nickname = nicknameField.getText();

        if (validateNickname(nickname)) {

            controlHandler.requestRooms();
            Stage stage = (Stage) enterButton.getScene().getWindow();
            stage.setScene(new Scene(roomsWindowController.getRoomsPane()));
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Nickname has illegal symbols");
            alert.show();
        }
    }

    private boolean validateNickname(String nickname) {
        if (nickname == null || nickname.equals("")) {
            return false;
        }
        return true;
    }
}

