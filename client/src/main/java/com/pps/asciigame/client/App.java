package com.pps.asciigame.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            final Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("main_scene.fxml"));
            primaryStage.setTitle("ASCII GAME");
            primaryStage.setScene(new Scene(parent, 800, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
