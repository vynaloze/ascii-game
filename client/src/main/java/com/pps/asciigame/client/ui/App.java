package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.ui.utils.ScenesManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ScenesManager.initParentScene(primaryStage);
        ScenesManager.loadScene(ScenesManager.Scenes.LOGIN);
    }
}
