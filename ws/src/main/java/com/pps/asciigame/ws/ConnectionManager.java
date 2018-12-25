package com.pps.asciigame.ws;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectionManager {
    @Autowired
    private Dispatcher dispatcher;
    private final List<Connection> connections = new ArrayList<>();

    public void addConnection(final Socket socket) {
        try {
            connections.add(new Connection(socket, dispatcher));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pushToAll(final Object message) {
        connections.forEach(c -> c.write(message));
    }

    //todo push to specific one

    //todo remove connection
}
