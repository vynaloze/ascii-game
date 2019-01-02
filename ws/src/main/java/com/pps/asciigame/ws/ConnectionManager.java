package com.pps.asciigame.ws;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.UserNotFoundException;
import com.pps.asciigame.common.protocol.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConnectionManager {
    @Autowired
    private Dispatcher dispatcher;
    private final List<Connection> waitingConnections = new ArrayList<>();
    private final List<Connection> readyConnections = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    public void addConnection(final Socket socket) {
        try {
            final var connection = new Connection(socket, dispatcher);
            waitingConnections.add(connection);
            LOGGER.info("Anonymous connection established.");
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public void pushToAll(final Message message) {
        readyConnections.forEach(c -> c.write(message));
    }

    public void pushTo(final User user, final Message message) {
        readyConnections.stream()
                .filter(connection -> connection.getUser().equals(user))
                .findFirst()
                .ifPresentOrElse(
                        connection -> connection.write(message),
                        () -> {
                            throw new UserNotFoundException(); //todo maybe different ex?
                        }
                );
    }

    //todo remove connection

    @Scheduled(fixedRate = 500)
    private void checkForReadyConnections() {
        final var areReady = waitingConnections.stream()
                .filter(Connection::isReady)
                .collect(Collectors.toList());
        areReady.forEach(connection -> LOGGER.info("Logged in user: " + connection.getUser().getName()));
        waitingConnections.removeAll(areReady);
        readyConnections.addAll(areReady);
    }
}
