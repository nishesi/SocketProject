package ru.itis.snaky.client.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.gui.controllers.components.RoomViewController;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseHandler;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomsWindowController implements Initializable {
    @FXML
    public Button backButton;
    @FXML
    public Button updateRoomsButton;
    @Setter
    private ControlHandler controlHandler;
    @Setter
    private ResponseHandler responseHandler;
    @Setter
    private AuthenticationWindowController authenticationWindowController;
    @Getter
    @Setter
    private Pane roomsPane;
    @FXML
    private ListView<Room> roomsListView;

    @FXML
    public void updateRooms() {
        new Task<>() {
            @Override
            protected Void call() {
                List<Room> rooms = responseHandler.getRooms();

                Platform.runLater(() -> roomsListView.setItems(FXCollections.observableList(rooms)));
                return null;
            }
        };

    }

    @FXML
    public void roomChosen() {
        Room room = roomsListView.getSelectionModel().getSelectedItem();

        controlHandler.sendChosenRoom(room);

        Stage stage = (Stage) roomsListView.getScene().getWindow();
        stage.setScene(
                new GameWindowController(this, controlHandler)
                        .getGamePane()
                        .getScene());
    }

    @FXML
    public void toAuthWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(authenticationWindowController.getAuthenticationPane().getScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        roomsListView.setCellFactory(roomListView -> new ListCell<>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);

                if (empty || room == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    RoomViewController roomViewController = new RoomViewController(room);

                    setGraphic(roomViewController.getView());
                }
            }
        });
    }
}
