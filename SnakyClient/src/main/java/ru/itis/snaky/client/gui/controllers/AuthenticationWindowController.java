package ru.itis.snaky.client.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.MessageHandler;
import ru.itis.snaky.client.handlers.ResponseObserver;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.AuthenticationParams;

public class AuthenticationWindowController {
    @Setter
    private ControlHandler controlHandler;
    @Setter
    private ResponseObserver responseObserver;

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

        if (!validateNickname(nickname)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nickname has illegal symbols");
            alert.show();
            return;
        }

        controlHandler.sendAuthMessage(nickname);

        Stage stage = (Stage) enterButton.getScene().getWindow();

        responseObserver.addHandler(MessageType.AUTHORIZATION,(MessageHandler<AuthenticationParams>) params -> {

            Platform.runLater(() -> {
                if (params.isSuccess()) {

                    stage.setScene(
                            roomsWindowController.getRoomsPane().getScene());
                    roomsWindowController.updateRooms();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again");
                    alert.show();
                }
            });
        });
    }

    private boolean validateNickname(String nickname) {
        return nickname != null && !nickname.equals("");
    }
}

