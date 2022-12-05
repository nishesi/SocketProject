package ru.itis.snaky.client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.view.RoomController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomsWindowController implements Initializable {
    @Getter
    @Setter
    private AnchorPane roomsWindow;

    @FXML
    private ListView<Room> listView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setRoomList(List<Room> roomList) {
        listView.setItems(FXCollections.observableList(roomList));
        listView.setCellFactory(roomListView -> new ListCell<>() {
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
        Room room = listView.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) listView.getScene().getWindow();
        stage.setScene(new Scene(loadGameWindow()));
    }

    private AnchorPane loadGameWindow() {
        GameWindowController gameWindowController = new GameWindowController();
        return gameWindowController.getAnchorPane();
    }
}
