package com.pps.asciigame.client;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

@ComponentScan
public class Main {
    @Autowired
    private Dispatcher dispatcher;

    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Main.class);
        final var main = context.getBean(Main.class);
        main.start();
    }

    private void start() {
        final var user = new User("someUser");  //todo get username from UI
        try (final var socket = new Socket(Config.hostname(), Config.port())) {
            final var connection = new Connection(socket, dispatcher);
            connection.setUser(user);
            Thread.sleep(5000);
            connection.write(new Login(user));

            // TODO remove DEBUG STUFF
            final var chatEntry = new ChatEntry(LocalDateTime.now(), user, "MEssage");
            connection.write(chatEntry);
            Thread.sleep(5000);

            final var chatEntry2 = new ChatEntry(LocalDateTime.now(), user, "MEssage 2");
            connection.write(chatEntry2);
            Thread.sleep(5000);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
