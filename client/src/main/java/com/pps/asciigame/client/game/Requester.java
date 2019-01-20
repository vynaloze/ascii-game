package com.pps.asciigame.client.game;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.Login;
import com.pps.asciigame.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class Requester {
    private Connection connection;
    private User user;
    @Autowired
    private Dispatcher dispatcher;

    public void connect(final User user) {
        this.user = user;
        try {
            final var socket = new Socket(Config.hostname(), Config.port());
            connection = new Connection(socket, dispatcher);
            connection.setUser(user);
            connection.write(new Login(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(final Message message) {
        if (isConnected()) {
            connection.write(message);
        } else {
            throw new IllegalStateException("Client is not connected.");
        }
    }

    public boolean isConnected() {
        return connection != null && user != null;
    }

    public User getUser() {
        return user;
    }
}
