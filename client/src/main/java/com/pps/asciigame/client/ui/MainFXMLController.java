package com.pps.asciigame.client.ui;

import com.pps.asciigame.client.game.Requester;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.Resource;
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
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MainFXMLController {
    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);
    @Autowired
    private Requester requester;

    @FXML
    private Label chatHistory, resourceLabel;
    @FXML
    private TextField message;
    @FXML
    private Button send, buildBase;


    @FXML
    public void initialize() {
        send.setOnAction(e -> sendChatMessage());
        buildBase.setOnAction(e -> ScenesManager.loadScene(ScenesManager.Scenes.BUILD));
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
        updateResources(basicInfo.getResources());
    }

    private void updateResources(final List<Resource> resources) {
        resources.sort(Comparator.comparingInt(o -> o.getType().ordinal()));
        final var format = "|| %s:    %10d || %s: %10d || %s:    %10d ||";
        final var text = String.format(format,
                resources.get(0).getType(), resources.get(0).getAmount().intValue(),
                resources.get(1).getType(), resources.get(1).getAmount().intValue(),
                resources.get(2).getType(), resources.get(2).getAmount().intValue());
        Platform.runLater(() -> resourceLabel.setText(text));
    }
}