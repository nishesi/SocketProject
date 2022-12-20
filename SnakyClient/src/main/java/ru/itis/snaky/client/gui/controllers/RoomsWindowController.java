package ru.itis.snaky.client.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import ru.itis.snaky.client.gui.controllers.components.RoomViewController;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseObserver;
import ru.itis.snaky.protocol.dto.TransferRoom;
import ru.itis.snaky.protocol.message.MessageType;

import java.net.URL;
import java.util.ArrayList;
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
    private ResponseObserver responseObserver;
    @Setter
    private AuthenticationWindowController authenticationWindowController;
    @Getter
    @Setter
    private Pane roomsPane;
    @FXML
    private ListView<Room> roomsListView;

    @FXML
    public void updateRooms() {
        controlHandler.requestRooms();

        responseObserver.addHandler(MessageType.ROOMS_LIST, message -> {
            List<Room> rooms = new ArrayList<>();
            List<TransferRoom> transferRooms = (List<TransferRoom>) (message.getParameter(0));

            transferRooms.stream()
                    .map(transferRoom -> new Room(transferRoom.getName(), transferRoom.getPlayers()))
                    .forEach(rooms::add);

            Platform.runLater(() -> roomsListView.setItems(FXCollections.observableList(rooms)));
        });
    }

    @FXML
    public void roomChosen() {
        Room room = roomsListView.getSelectionModel().getSelectedItem();
        if (room != null) {
            controlHandler.sendChosenRoom(room);

            Stage stage = (Stage) roomsListView.getScene().getWindow();

            responseObserver.addHandler(MessageType.CHOSEN_ROOM, message -> {
                Platform.runLater(() -> {
                    if (message.getParameter(0).equals("1")) {

                        stage.setScene(
                                new GameWindowController(this, controlHandler, responseObserver)
                                        .getGamePane()
                                        .getScene());

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
