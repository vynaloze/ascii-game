package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.model.OperationType;
import com.pps.asciigame.common.model.ResourceAmounts;
import com.pps.asciigame.common.protocol.PerformOperation;
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
public class PerformOperationFXMLController {
    @Autowired
    private Requester requester;
    @Autowired
    private ParameterForwarder parameterForwarder;
    private Base targetBase;
    private ResourceAmounts currentResources;
    private OperationType selectedOperation;

    @FXML
    private Label targetBaseLabel, selectedOperationLabel;
    @FXML
    private ListView<OperationType> availableOperations;
    @FXML
    private Button performOperation, exitButton;

    @FXML
    public void initialize() {
        targetBase = parameterForwarder.get(Base.class);
        targetBaseLabel.setText("Target base: " + targetBase.getName() + " Available operations:");
        currentResources = parameterForwarder.get(ResourceAmounts.class);
        initListView();
        performOperation.setOnAction(e -> performOperation());
        exitButton.setOnAction(e -> closeStage());
    }

    private void initListView() {
        final var possibleToPerform = Stream.of(OperationType.values())
                .filter(type -> type.getCosts().isWithin(currentResources))
                .collect(Collectors.toList());
        availableOperations.setItems(FXCollections.observableArrayList(possibleToPerform));
        availableOperations.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(OperationType type, boolean empty) {
                super.updateItem(type, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(type + ";Cost:" + type.getCosts() + ";Effect:" + type.getEffect() + ";Percent:" + type.getPercent());
                }
            }
        });

        availableOperations.setOnMouseClicked(e -> {
            selectedOperation = availableOperations.getSelectionModel().getSelectedItem();
            selectedOperationLabel.setText(selectedOperation.toString());
            performOperation.setVisible(true);
        });
    }

    private void performOperation() {
        if (targetBase != null && selectedOperation != null) {
            requester.sendRequest(new PerformOperation(requester.getUser(), new Operation(null, targetBase, selectedOperation)));
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
