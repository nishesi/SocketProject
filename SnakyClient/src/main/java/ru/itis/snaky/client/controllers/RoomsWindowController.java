package ru.itis.snaky.client.controllers;

import javafx.collections.FXCollections;
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
import ru.itis.snaky.client.view.RoomController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomsWindowController implements Initializable {
    @Setter
    private AuthenticationWindowController authenticationWindowController;

    @Getter
    @Setter
    private Pane roomsPane;

    @FXML
    public Button backButton;
    @FXML
    private ListView<Room> roomsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setRoomList(List<Room> roomList) {
        roomsListView.setItems(FXCollections.observableList(roomList));
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

    @FXML
    public void roomChosen() {
        Room room = roomsListView.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) roomsListView.getScene().getWindow();
        stage.setScene(new Scene(loadGameWindow()));
    }

    private Pane loadGameWindow() {
        GameWindowController gameWindowController = new GameWindowController(this);
        return gameWindowController.getGamePane();
    }

    public void toAuthWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(authenticationWindowController.getAuthenticationPane().getScene());
    }
}
