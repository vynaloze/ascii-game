package com.pps.asciigame.client.ui.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.pps.asciigame.client.ui.utils.ScenesManager.Scenes.MAIN;

public class ScenesManager {
    private static final Logger LOGGER = LogManager.getLogger(ScenesManager.class);
    private static final SpringFXMLLoader loader = new SpringFXMLLoader();
    private static Scene parentScene;

    public static void initParentScene(final Stage primaryStage) {
        try {
            final var parent = (Parent) loader.load(MAIN.getResourceUrl());
            primaryStage.setTitle("ASCII GAME");
            parentScene = new Scene(parent, 800, 600);
            primaryStage.setScene(parentScene);
            primaryStage.show();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static void loadScene(final Scenes scene) {
        final Stage stage = new Stage();
        try {
            final var parent = (Parent) loader.load(scene.getResourceUrl());
            stage.setTitle(scene.name());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(parentScene.getWindow());
            stage.setScene(new Scene(parent, 600, 400));
            stage.showAndWait();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public enum Scenes {
        MAIN("/main.fxml"), LOGIN("/login.fxml"), BUILD_BASE("/buildBase.fxml"), BUILD_BUILDING("/buildBuilding.fxml");

        private final String resource;

        Scenes(final String resource) {
            this.resource = resource;
        }

        public String getResourceUrl() {
            return resource;
        }
    }
}
