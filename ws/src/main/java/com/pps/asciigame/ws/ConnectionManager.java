package com.pps.asciigame.ws;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
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
            e.printStackTrace();
        }
    }

    public void pushToAll(final Object message) {
        readyConnections.forEach(c -> c.write(message));
    }

    //todo push to specific one

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
