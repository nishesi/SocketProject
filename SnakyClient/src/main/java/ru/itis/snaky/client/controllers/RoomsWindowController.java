package ru.itis.snaky.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.dto.Room;

public class RoomsWindowController {

    @Getter
    @Setter
    private AnchorPane roomsWindow;

    @FXML
    private ListView<Room> list;

}
