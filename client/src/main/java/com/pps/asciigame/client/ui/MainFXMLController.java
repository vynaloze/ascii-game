package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.common.protocol.ChatEntry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MainFXMLController {
    @Autowired
    private Requester requester;

    @FXML
    private Label chatHistory;
    @FXML
    private TextField message;
    @FXML
    private Button send;

    @FXML
    public void initialize() {
        send.setOnAction(e -> sendChatMessage());
    }

    public void updateChatHistory(final ChatEntry chatEntry) {
        // this is dirty hack for "IllegalStateException: Not on FX application thread"
        Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + "\n" + chatEntry));
    }

    private void sendChatMessage() {
        requester.sendRequest(new ChatEntry(LocalDateTime.now(), requester.getUser(), message.getText()));
        message.clear();
    }
}