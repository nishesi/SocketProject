package ru.itis.snaky.client.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.itis.snaky.client.dto.Room;
import ru.itis.snaky.client.dto.converters.Converters;
import ru.itis.snaky.client.gui.controllers.components.RoomViewController;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.MessageHandler;
import ru.itis.snaky.client.handlers.ResponseObserver;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.MessageType;
import ru.itis.snaky.protocol.message.parameters.ChosenRoomParams;
import ru.itis.snaky.protocol.message.parameters.RoomsListParams;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RoomsWindowController implements Initializable {
    @Setter
    private ControlHandler controlHandler;
    @Setter
    private ResponseObserver responseObserver;
    @Setter
    private AuthenticationWindowController authenticationWindowController;
    @Getter
    @Setter
    private Pane roomsPane;
    private ObservableList<Room> roomsList;
    @FXML
    public Button updateRoomsButton;
    @FXML
    public Button backButton;
    @FXML
    private ListView<Room> roomsListView;

    @FXML
    public void updateRooms() {
        controlHandler.requestRooms();

        responseObserver.addHandler(MessageType.ROOMS_LIST, (MessageHandler<RoomsListParams>) params -> {

            TransferRoom[] transferRooms = params.getRooms();

            Platform.runLater(() -> {
                roomsList.clear();
                Arrays.stream(transferRooms)
                        .map(Converters::from)
                        .forEach(roomsList::add);
            });
        });
    }

    @FXML
    public void roomChosen() {
        Room room = roomsListView.getSelectionModel().getSelectedItem();
        if (room != null) {
            controlHandler.sendChosenRoom(room);

            Stage stage = (Stage) roomsListView.getScene().getWindow();

            responseObserver.addHandler(MessageType.CHOSEN_ROOM, (MessageHandler<ChosenRoomParams>) params -> {
                Platform.runLater(() -> {
                    if (params.isSuccess()) {
                        Pane pane = new GameWindowController(this, controlHandler, responseObserver,
                                room.getSize(), room.getColorsArray()).getGamePane();

                        stage.setScene(pane.getScene());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Can't enter room");
                        alert.show();
                    }
                });
            });
        }
    }

    @FXML
    public void toAuthWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(authenticationWindowController.getAuthenticationPane().getScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomsList = FXCollections.observableArrayList();
        roomsListView.setItems(roomsList);

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
