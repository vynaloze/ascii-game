package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.client.ui.map.MapUI;
import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.client.ui.utils.ScenesManager;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.ResourceAmounts;
import com.pps.asciigame.common.protocol.BasicInfo;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.RequestBasicInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class MainFXMLController {
    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
    @Autowired
    private Requester requester;
    @Autowired
    private ParameterForwarder parameterForwarder;

    private ResourceAmounts currentResources;
    private Base selectedBase;

    @FXML
    private Label chatHistory, resourceLabel, currentBaseLabel;
    @FXML
    private TextField message;
    @FXML
    private Button send, buildBase, buildBuilding, map;
    @FXML
    private ListView<Base> basesList;
    @FXML
    private ListView<Building> buildingsList;
    private List<Building> allBuildings;


    @FXML
    public void initialize() {
        // chat
        send.setOnAction(e -> sendChatMessage());
        message.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                sendChatMessage();
            }
        });
        // build base
        buildBase.setOnAction(e -> ScenesManager.loadScene(ScenesManager.Scenes.BUILD_BASE));
        // build building
        buildBuilding.setOnAction(e -> {
            parameterForwarder.pass(currentResources, ResourceAmounts.class);
            parameterForwarder.pass(selectedBase, Base.class);
            ScenesManager.loadScene(ScenesManager.Scenes.BUILD_BUILDING);
        });
        // periodic update
        scheduler.scheduleAtFixedRate(this::requestBasicInfo, 0, Config.updatePeriod(), TimeUnit.SECONDS);
        // listviews
        initBaseListView();
        initBuildingsListView();
        // map
        map.setOnAction(e -> ScenesManager.loadScene(MapUI.class));
    }

    private void sendChatMessage() {
        requester.sendRequest(new ChatEntry(LocalDateTime.now(), requester.getUser(), message.getText()));
        message.clear();
    }

    public void updateChatHistory(final ChatEntry chatEntry) {
        // this is dirty hack for "IllegalStateException: Not on FX application thread"
        Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + "\n" + chatEntry));
    }

    private void requestBasicInfo() {
        if (requester.isConnected()) {
            requester.sendRequest(new RequestBasicInfo(requester.getUser()));
        }
    }

    public void updateBasicInfo(final BasicInfo basicInfo) {
        Platform.runLater(() -> {
            updateResources(basicInfo.getResources());
            updateBases(basicInfo.getBases());
        });
        updateBuildings(basicInfo.getBuildings());
        if (selectedBase != null) {
            showBuildingsForCurrentBase(selectedBase);
        }
    }

    private void updateResources(final List<Resource> resources) {
        currentResources = new ResourceAmounts.Builder().fromList(resources);
        resources.sort(Comparator.comparingInt(o -> o.getType().ordinal()));
        final var format = "|| %s:    %10d || %s: %10d || %s:    %10d ||";
        final var text = String.format(format,
                resources.get(0).getType(), resources.get(0).getAmount().intValue(),
                resources.get(1).getType(), resources.get(1).getAmount().intValue(),
                resources.get(2).getType(), resources.get(2).getAmount().intValue());
        resourceLabel.setText(text);
    }

    private void initBaseListView() {
        basesList.setItems(FXCollections.observableList(new ArrayList<>()));
        basesList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Base base, boolean empty) {
                super.updateItem(base, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(base.getName() + " (" + base.getX() + "," + base.getY() + ")");
                }
            }
        });
        basesList.setOnMouseClicked(e -> {
            final var base = basesList.getSelectionModel().getSelectedItem();
            selectedBase = base;
            showBuildingsForCurrentBase(base);
            buildBuilding.setVisible(true);
        });
    }

    private void showBuildingsForCurrentBase(final Base base) {
        Platform.runLater(() -> {
            currentBaseLabel.setText("Selected base: " + base.getName() + " Buildings:");
            buildingsList.getItems().clear();
            buildingsList.getItems().addAll(allBuildings.stream().filter(building -> building.getBase().equals(base)).collect(Collectors.toList()));

        });
    }

    private void updateBases(final List<Base> bases) {
        basesList.getItems().clear();
        basesList.getItems().addAll(bases);
    }

    private void initBuildingsListView() {
        buildingsList.setItems(FXCollections.observableList(new ArrayList<>()));
        buildingsList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Building building, boolean empty) {
                super.updateItem(building, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(building.getType() + ": " + building.getProfits());
                }
            }
        });
    }

    private void updateBuildings(final List<Building> buildings) {
        allBuildings = buildings;
    }
}