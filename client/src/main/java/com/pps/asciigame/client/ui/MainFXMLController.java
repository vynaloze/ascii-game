package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.protocol.BasicInfo;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.RequestBasicInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MainFXMLController {
    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
    @Autowired
    private Requester requester;

    @FXML
    private Label chatHistory, basicInfoLabel;
    @FXML
    private TextField message;
    @FXML
    private Button send;


    @FXML
    public void initialize() {
        send.setOnAction(e -> sendChatMessage());
        scheduler.scheduleAtFixedRate(this::requestBasicInfo, 0, Config.updatePeriod(), TimeUnit.SECONDS);
    }

    // CHAT
    private void sendChatMessage() {
        requester.sendRequest(new ChatEntry(LocalDateTime.now(), requester.getUser(), message.getText()));
        message.clear();
    }

    public void updateChatHistory(final ChatEntry chatEntry) {
        // this is dirty hack for "IllegalStateException: Not on FX application thread"
        Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + "\n" + chatEntry));
    }

    // BASIC INFO
    private void requestBasicInfo() {
        if (requester.isConnected()) {
            requester.sendRequest(new RequestBasicInfo(requester.getUser()));
        }
    }

    public void updateBasicInfo(final BasicInfo basicInfo) {
        Platform.runLater(() -> basicInfoLabel.setText(basicInfo.toString()));
    }
}