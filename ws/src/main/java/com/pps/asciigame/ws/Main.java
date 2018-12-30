package com.pps.asciigame.ws;

import com.pps.asciigame.common.configuration.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
@ComponentScan
@EnableScheduling
public class Main {
    @Autowired
    private ConnectionManager connectionManager;

    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Main.class);
        final var main = context.getBean(Main.class);
        main.start();
    }

    private void start() {
        try (final var serverSocket = new ServerSocket(Config.port())) {
            while (true) {
                connectionManager.addConnection(serverSocket.accept());
            }
        } catch (final IOException e) {
            System.err.println("Could not listen on port ");
            System.exit(-1);
        }
    }
}
