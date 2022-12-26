package ru.itis.snaky.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itis.snaky.client.core.Connection;
import ru.itis.snaky.client.gui.controllers.AuthenticationWindowController;
import ru.itis.snaky.client.gui.controllers.RoomsWindowController;
import ru.itis.snaky.client.handlers.ControlHandler;
import ru.itis.snaky.client.handlers.ResponseObserver;

import java.io.IOException;
import java.net.InetAddress;

public class App extends Application {
    private Connection connection;

    private ControlHandler controlHandler;
    private ResponseObserver responseObserver;

    private AuthenticationWindowController authenticationWindowController;
    private RoomsWindowController roomsWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        connection = new Connection(InetAddress.getLocalHost(), (short) 7777);
        initHandlers();
        initAuthenticationWindowController();
        initRoomsWindowController();
        authenticationWindowController.setRoomsWindowController(roomsWindowController);

        primaryStage.setScene(authenticationWindowController.getAuthenticationPane().getScene());
        primaryStage.show();

    }

    private void initHandlers() {
        controlHandler = new ControlHandler(connection.getOutputStreamThread());
        responseObserver = new ResponseObserver(connection.getInputStreamThread());

        responseObserver.start();
    }

    private void initAuthenticationWindowController() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane authenticationWindow = loader.load(App.class.getResourceAsStream("/layout/authenticationWindow.fxml"));
        authenticationWindow.getStylesheets().add("/css/main.css");
        authenticationWindow.getStylesheets().add("/css/authentication-window.css");

        AuthenticationWindowController authenticationWindowController = loader.getController();
        authenticationWindowController.setAuthenticationPane(authenticationWindow);

        authenticationWindowController.setControlHandler(controlHandler);
        authenticationWindowController.setResponseObserver(responseObserver);

        this.authenticationWindowController = authenticationWindowController;
        new Scene(authenticationWindow);
    }

    private void initRoomsWindowController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane roomsWindow = loader.load(App.class.getResourceAsStream("/layout/roomsWindow.fxml"));
        new Scene(roomsWindow);
        roomsWindow.getStylesheets().add("/css/main.css");
        roomsWindow.getStylesheets().add("/css/rooms-window.css");

        RoomsWindowController roomsWindowController = loader.getController();
        roomsWindowController.setRoomsPane(roomsWindow);
        roomsWindowController.setAuthenticationWindowController(authenticationWindowController);


        roomsWindowController.setControlHandler(controlHandler);
        roomsWindowController.setResponseObserver(responseObserver);

        this.roomsWindowController = roomsWindowController;
    }

    @Override
    public void stop() {
        controlHandler.sendCloseMessage();
        connection.close();
    }
}
