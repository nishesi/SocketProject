package ru.itis.snaky.client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseHandler;
import ru.itis.snaky.client.view.RoomController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomsWindowController implements Initializable {
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
    public Button backButton;
    @FXML
    public Button updateRoomsButton;
    @FXML
    private ListView<Room> roomsListView;

    @FXML
    public void updateRooms() {
        Task<Void> rooms = new Task<>() {
            @Override
            protected Void call() {
                List<Room> rooms = responseHandler.getRooms();

                Platform.runLater(() -> {
                    roomsListView.setItems(FXCollections.observableList(rooms));
                });
                return null;
            }
        };

    }

    @FXML
    public void roomChosen() {
        Room room = roomsListView.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) roomsListView.getScene().getWindow();
        stage.setScene(new Scene(loadGameWindow()));
    }

    @FXML
    private Pane loadGameWindow() {
        GameWindowController gameWindowController = new GameWindowController(this);
        return gameWindowController.getGamePane();
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
                    RoomController roomController = new RoomController(room);

                    setGraphic(roomController.getView());
                }
            }
        });
    }
}
