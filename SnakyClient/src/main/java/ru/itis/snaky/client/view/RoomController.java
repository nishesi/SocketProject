package ru.itis.snaky.client.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ru.itis.snaky.client.dto.Room;

import java.io.IOException;

public class RoomController {

    @FXML
    private HBox box;
    @FXML
    private Label name;

    @FXML
    private Label playersCount;

    public RoomController(Room room) {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomController.class.getResource("/components/Room.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        name.setText(room.getName());
        playersCount.setText(room.getPlayersCount() + " players");
    }

    public HBox getView() {
        return box;
    }
}
