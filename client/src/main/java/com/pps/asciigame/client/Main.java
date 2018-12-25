package com.pps.asciigame.client;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.messages.ChatEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

@ComponentScan
public class Main {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    @Autowired
    private Dispatcher dispatcher;

    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Main.class);
        final var main = context.getBean(Main.class);
        main.start();
    }

    private void start() {
        try (final var socket = new Socket(HOSTNAME, PORT)) {
            final var connection = new Connection(socket, dispatcher);

            // TODO remove DEBUG STUFF
            final var chatEntry = new ChatEntry(LocalDateTime.now(), "Author", "MEssage");
            connection.write(chatEntry);
            Thread.sleep(5000);

            final var chatEntry2 = new ChatEntry(LocalDateTime.now(), "Author", "MEssage 2");
            connection.write(chatEntry2);
            Thread.sleep(5000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
