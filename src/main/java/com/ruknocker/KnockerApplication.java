package com.ruknocker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KnockerApplication extends Application {
    private static Stage mainWindow;
    @Override
    public void start(Stage stage) throws IOException {
        mainWindow = stage;
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        stage.setTitle("RuKnocker");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getMainWindow() {
        return mainWindow;
    }
}