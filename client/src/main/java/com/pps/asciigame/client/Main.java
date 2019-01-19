package com.pps.asciigame.client;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.BuildingType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.BuildBase;
import com.pps.asciigame.common.protocol.BuildBuilding;
import com.pps.asciigame.common.protocol.Login;
import com.pps.asciigame.common.protocol.RequestBasicInfo;
import com.pps.asciigame.common.util.BuildingFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.Socket;

@ComponentScan
public class Main {
    @Autowired
    private Dispatcher dispatcher;

    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Main.class);
        final var main = context.getBean(Main.class);
//        main.start();
        App.main(args);
    }

    private void start() {
        final var user = new User("someUser");  //todo get username from UI
        try (final var socket = new Socket(Config.hostname(), Config.port())) {
            final var connection = new Connection(socket, dispatcher);
            connection.setUser(user);
//            Thread.sleep(5000);
            connection.write(new Login(user));

            // TODO remove DEBUG STUFF
//            final var chatEntry = new ChatEntry(LocalDateTime.now(), user, "MEssage");
//            connection.write(chatEntry);
//            Thread.sleep(5000);
//
//            final var chatEntry2 = new ChatEntry(LocalDateTime.now(), user, "MEssage 2");
//            connection.write(chatEntry2);
//            Thread.sleep(5000);

            final var base = new Base(6, 9, "mybase", user);
            connection.write(new BuildBase(user, base));
            Thread.sleep(10000);

            final var building = BuildingFactory.createBuilding(base, BuildingType.A);
            connection.write(new BuildBuilding(user, building));
            Thread.sleep(10000);

            final var basicInfoRequest = new RequestBasicInfo(user);
            connection.write(basicInfoRequest);
            Thread.sleep(5000);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
