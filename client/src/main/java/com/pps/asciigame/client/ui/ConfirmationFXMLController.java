package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.common.protocol.Confirmation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationFXMLController {
    @Autowired
    private ParameterForwarder parameterForwarder;
    @FXML
    private Label info;
    @FXML
    private Button OK;

    @FXML
    public void initialize() {
        final var confirmation = parameterForwarder.get(Confirmation.class);
        info.setText(confirmation.getContents());
        OK.setOnAction(e -> closeStage());
    }

    private void closeStage() {
        parameterForwarder.remove(Confirmation.class);
        final var stage = (Stage) OK.getScene().getWindow();
        stage.close();
    }
}
