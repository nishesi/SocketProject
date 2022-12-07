package ru.itis.snaky.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itis.snaky.client.controllers.AuthenticationWindowController;
import ru.itis.snaky.client.controllers.RoomsWindowController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane authenticationWindow = initContext();

        primaryStage.setScene(new Scene(authenticationWindow));
        primaryStage.show();

    }

    private Pane initContext() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane authenticationWindow = loader.load(App.class.getResourceAsStream("/layout/AuthenticationWindow.fxml"));
        AuthenticationWindowController authenticationWindowController = loader.getController();
        authenticationWindow.getStylesheets().add("/css/main.css");
        authenticationWindow.getStylesheets().add("/css/AuthenticationWindow.css");

        loader = new FXMLLoader();
        Pane roomsWindow = loader.load(App.class.getResourceAsStream("/layout/RoomsWindow.fxml"));
        RoomsWindowController roomsWindowController = loader.getController();
        roomsWindow.getStylesheets().add("/css/main.css");
        roomsWindow.getStylesheets().add("/css/RoomsWindow.css");

        authenticationWindowController.setAuthenticationWindow(authenticationWindow);
        authenticationWindowController.setRoomsWindowController(roomsWindowController);

        roomsWindowController.setRoomsWindow(roomsWindow);


        return authenticationWindow;
    }
}
