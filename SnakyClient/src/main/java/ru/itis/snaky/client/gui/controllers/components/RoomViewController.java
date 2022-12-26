package ru.itis.snaky.client.gui.controllers.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import ru.itis.snaky.client.dto.Room;

import java.io.IOException;

public class RoomViewController {

    @FXML
    private Pane box;
    @FXML
    private Label name;

    @FXML
    private Label playersCount;

    public RoomViewController(Room room) {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomViewController.class.getResource("/layout/components/RoomView.fxml"));
        fxmlLoader.setController(this);
        try {
            Pane box = fxmlLoader.load();
            box.getStylesheets().add("/css/components/Room.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        name.setText(room.getName());
        playersCount.setText(room.getPlayersCount() + " players");
    }

    public Pane getView() {
        return box;
    }
}
