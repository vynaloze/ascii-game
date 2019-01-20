package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.BuildingType;
import com.pps.asciigame.common.model.ResourceAmounts;
import com.pps.asciigame.common.protocol.BuildBuilding;
import com.pps.asciigame.common.util.BuildingFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BuildBuildingFXMLController {
    @Autowired
    private Requester requester;
    @Autowired
    private ParameterForwarder parameterForwarder;
    private Base currentBase;
    private ResourceAmounts currentResources;
    private BuildingType selectedBuilding;

    @FXML
    private Label currentBaseLabel;
    @FXML
    private ListView<BuildingType> availableBuildings;
    @FXML
    private Button buildBuildingButton, exitButton;

    @FXML
    public void initialize() {
        currentBase = parameterForwarder.get(Base.class);
        currentBaseLabel.setText("Selected base: " + currentBase.getName() + " Available buildings:");
        currentResources = parameterForwarder.get(ResourceAmounts.class);
        initListView();
        buildBuildingButton.setOnAction(e -> buildBuilding());
        exitButton.setOnAction(e -> closeStage());
    }

    private void initListView() {
        final var possibleToBuild = Stream.of(BuildingType.values())
                .filter(type -> type.getCost().isWithin(currentResources))
                .collect(Collectors.toList());
        availableBuildings.setItems(FXCollections.observableArrayList(possibleToBuild));
        availableBuildings.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(BuildingType type, boolean empty) {
                super.updateItem(type, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(type + ": " + type.getCost() + "; " + type.getProfit());
                }
            }
        });

        availableBuildings.setOnMouseClicked(e -> {
            selectedBuilding = availableBuildings.getSelectionModel().getSelectedItem();
            currentBaseLabel.setText(selectedBuilding.toString());
            buildBuildingButton.setVisible(true);
        });
    }

    private void buildBuilding() {
        if (currentBase != null && selectedBuilding != null) {
            requester.sendRequest(new BuildBuilding(requester.getUser(), BuildingFactory.createBuilding(currentBase, selectedBuilding)));
        }
        closeStage();
    }

    private void closeStage() {
        parameterForwarder.remove(Base.class);
        parameterForwarder.remove(ResourceAmounts.class);
        final var stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
