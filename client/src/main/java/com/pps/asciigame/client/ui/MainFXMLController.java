package com.pps.asciigame.client.ui;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.Login;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

@Component
public class MainFXMLController {
    private Connection connection;
    private User user;

    @Autowired
    private Dispatcher dispatcher;

    @FXML
    private Label chatHistory;

    @FXML
    private TextField message;

    @FXML
    private Button send;

    @FXML
    public void initialize() {
        connect();
        send.setOnAction(e -> sendChatMessage());
    }


    private void connect() {
        this.user = new User("someUser");  //todo get username from UI
        try {
            final var socket = new Socket(Config.hostname(), Config.port());
            this.connection = new Connection(socket, dispatcher);
            connection.setUser(user);
            connection.write(new Login(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateChatHistory(final ChatEntry chatEntry) {
        // this is dirty hack for "IllegalStateException: Not on FX application thread"
        Platform.runLater(() -> chatHistory.setText(chatHistory.getText() + "\n" + chatEntry));
    }

    private void sendChatMessage() {
        connection.write(new ChatEntry(LocalDateTime.now(), user, message.getText()));
        message.clear();
    }
}