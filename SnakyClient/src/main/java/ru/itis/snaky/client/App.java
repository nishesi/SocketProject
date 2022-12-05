package ru.itis.snaky.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.itis.snaky.client.controllers.AuthenticationWindowController;
import ru.itis.snaky.client.controllers.GameWindowController;
import ru.itis.snaky.client.controllers.RoomsWindowController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane authenticationWindow = initContext();

        primaryStage.setScene(new Scene(authenticationWindow));
        primaryStage.show();

    }

    private AnchorPane initContext() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane authenticationWindow = loader.load(App.class.getResourceAsStream("/layout/AuthenticationWindow.fxml"));
        AuthenticationWindowController authenticationWindowController = loader.getController();

        loader = new FXMLLoader();
        AnchorPane roomsWindow = loader.load(App.class.getResourceAsStream("/layout/RoomsWindow.fxml"));
        RoomsWindowController roomsWindowController = loader.getController();

        loader = new FXMLLoader();
        AnchorPane gameWindow = loader.load(App.class.getResourceAsStream("/layout/GameWindow.fxml"));
        GameWindowController gameWindowController = loader.getController();

        authenticationWindowController.setAuthenticationWindow(authenticationWindow);
        authenticationWindowController.setRoomsWindowController(roomsWindowController);

        roomsWindowController.setRoomsWindow(roomsWindow);

        gameWindowController.setGameWindow(gameWindow);

        return authenticationWindow;
    }
}
