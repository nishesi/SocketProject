package ru.itis.snaky.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itis.snaky.client.gui.controllers.AuthenticationWindowController;
import ru.itis.snaky.client.gui.Direction;
import ru.itis.snaky.client.gui.controllers.RoomsWindowController;
import ru.itis.snaky.client.core.Connection;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.protocol.threads.OutputStreamThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class App extends Application {
    private Connection connection;

    private AuthenticationWindowController authenticationWindowController;
    private RoomsWindowController roomsWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        connection = new Connection(InetAddress.getLocalHost(), (short) 7777);
        initAuthenticationWindowController();
        initRoomsWindowController();

        primaryStage.setScene(new Scene(authenticationWindowController.getAuthenticationPane()));
        primaryStage.show();

    }

    private void initAuthenticationWindowController() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane authenticationWindow = loader.load(App.class.getResourceAsStream("/layout/AuthenticationWindow.fxml"));
        authenticationWindow.getStylesheets().add("/css/main.css");
        authenticationWindow.getStylesheets().add("/css/AuthenticationWindow.css");

        AuthenticationWindowController authenticationWindowController = loader.getController();
        authenticationWindowController.setAuthenticationPane(authenticationWindow);

        this.authenticationWindowController = authenticationWindowController;
    }

    private void initRoomsWindowController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane roomsWindow = loader.load(App.class.getResourceAsStream("/layout/RoomsWindow.fxml"));
        roomsWindow.getStylesheets().add("/css/main.css");
        roomsWindow.getStylesheets().add("/css/RoomsWindow.css");

        RoomsWindowController roomsWindowController = loader.getController();
        roomsWindowController.setRoomsPane(roomsWindow);
        roomsWindowController.setAuthenticationWindowController(authenticationWindowController);
        roomsWindowController.setControlHandler(new ControlHandler(new OutputStreamThread(new ByteArrayOutputStream())) {
            @Override
            public void requestRooms() {

            }

            @Override
            public void sendDirection(Direction direction) {
                System.out.println(direction.name());
            }
        });

        this.roomsWindowController = roomsWindowController;
    }

    @Override
    public void stop() {
        connection.close();
    }
}
