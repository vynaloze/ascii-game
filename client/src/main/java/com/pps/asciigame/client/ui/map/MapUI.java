package com.pps.asciigame.client.ui.map;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.client.ui.CodedScene;
import com.pps.asciigame.client.ui.utils.Coord;
import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.client.ui.utils.ScenesManager;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.protocol.RequestMapData;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MapUI implements CodedScene {
    @Autowired
    private Requester requester;
    @Autowired
    private ParameterForwarder parameterForwarder;

    private static final int MAP_SIZE = 9;

    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    private final Stage stage = new Stage();
    private final VBox vBox = new VBox();

    private int currentX = 0;
    private int currentY = 0;

    private List<Base> bases = new ArrayList<>();

    private boolean initialized = false;

    @Override
    public void show() {
        if (!initialized) {
            scheduler.scheduleAtFixedRate(() -> requester.sendRequest(new RequestMapData(requester.getUser())), 1, 1, TimeUnit.SECONDS);
            reloadMap();
            final var scene = new Scene(vBox, 800, 800);
            stage.setTitle("MAP");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            initialized = true;
        }
        stage.show();
    }

    private synchronized void reloadMap() {
        vBox.getChildren().clear();
        vBox.getChildren().addAll(prepareControlButtons(), prepareMap(currentX, currentY));
    }

    private HBox prepareControlButtons() {
        final Button left = new Button("X-1");
        left.setOnAction(e -> {
            currentX -= 1;
            reloadMap();
        });
        final Button right = new Button("X+1");
        right.setOnAction(e -> {
            currentX += 1;
            reloadMap();
        });
        final Button down = new Button("Y-1");
        down.setOnAction(e -> {
            currentY -= 1;
            reloadMap();
        });
        final Button up = new Button("Y+1");
        up.setOnAction(e -> {
            currentY += 1;
            reloadMap();
        });
        final Button exit = new Button("Exit");
        exit.setOnAction(e -> stage.hide());

        final var hbox = new HBox();
        hbox.getChildren().addAll(left, right, up, down, exit);
        return hbox;
    }

    private GridPane prepareMap(final int x, final int y) {
        final var pane = new GridPane();
        pane.setGridLinesVisible(true);
        for (int i = x, k = 0; i <= x + MAP_SIZE; i++, k++) {
            for (int j = y, l = 0; j <= y + MAP_SIZE; j++, l++) {
                final var button = new Button();
                prepareButton(button, i, j);
                pane.add(button, k, l);
            }
        }
        for (int i = 0; i < MAP_SIZE; i++) {
            pane.getColumnConstraints().add(new ColumnConstraints(60, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(60, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        return pane;
    }

    private void prepareButton(final Button button, final int x, final int y) {
        final Optional<Base> base = bases.stream().filter(b -> b.getX() == x && b.getY() == y).findFirst();
        if (base.isEmpty()) {
            final var text = "        \n EMPTY  \n        ";
            button.setText(text);
            button.setOnAction(e -> {
                parameterForwarder.pass(new Coord(x, y), Coord.class);
                ScenesManager.loadScene(ScenesManager.Scenes.BUILD_BASE);
            });
            return;
        }
        final var format = "%8s\n%8s\n%8s";
        final var text = String.format(format, x + "," + y, base.get().getName(), base.get().getOwner().getName());
        button.setText(text);
        if (base.get().getOwner().equals(requester.getUser())) {
            button.setOnAction(e -> {
                parameterForwarder.pass(base.get(), Base.class);
                ScenesManager.loadScene(ScenesManager.Scenes.BUILD_BUILDING);
            });
        } else {
            button.setOnAction(e -> {
                // todo operations here
            });
        }
    }

    @Override
    public void initOwner(final Window window) {
        if (!initialized) {
            stage.initOwner(window);
        }
    }

    public void setBases(final List<Base> bases) {
        this.bases = bases;
        Platform.runLater(this::reloadMap);
    }
}
