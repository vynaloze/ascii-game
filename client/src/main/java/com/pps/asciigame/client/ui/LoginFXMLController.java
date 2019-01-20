package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.common.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginFXMLController {
    @Autowired
    private Requester requester;

    @FXML
    private TextField username;
    @FXML
    private Button login;

    @FXML
    public void initialize() {
        login.setOnAction(e -> connect());
    }

    private void connect() {
        final var user = new User(username.getText());
        requester.connect(user);
        closeStage();
    }

    private void closeStage() {
        final var stage = (Stage) login.getScene().getWindow();
        stage.close();
    }
}
