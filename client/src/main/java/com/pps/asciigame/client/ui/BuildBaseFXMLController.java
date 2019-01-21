package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.client.ui.utils.Coord;
import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.protocol.BuildBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildBaseFXMLController {
    @Autowired
    private Requester requester;
    @Autowired
    private ParameterForwarder parameterForwarder;

    @FXML
    private TextField buildBaseName;
    @FXML
    private Button buildBaseButton, exitButton;

    @FXML
    public void initialize() {
        buildBaseButton.setOnAction(e -> buildBase());
        exitButton.setOnAction(e -> closeStage());
        buildBaseName.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                buildBase();
                closeStage();
            }
        });
    }

    private void closeStage() {
        final var stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void buildBase() {
        final var coord = parameterForwarder.get(Coord.class);
        final var base = new Base(coord.getX(), coord.getY(), buildBaseName.getText(), requester.getUser());
        requester.sendRequest(new BuildBase(requester.getUser(), base));
        parameterForwarder.remove(Coord.class);
        closeStage();
    }
}
