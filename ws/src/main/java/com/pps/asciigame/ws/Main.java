package com.pps.asciigame.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.ServerSocket;

@ComponentScan
public class Main {
    private final static int portNumber = 8080;
    @Autowired
    private ConnectionManager connectionManager;

    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Main.class);
        final var main = context.getBean(Main.class);
        main.start();
    }

    private void start() {
        try (final var serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                connectionManager.addConnection(serverSocket.accept());
            }
        } catch (final IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
