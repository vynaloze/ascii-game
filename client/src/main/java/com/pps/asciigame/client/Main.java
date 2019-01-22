package com.pps.asciigame.client;

import com.pps.asciigame.client.ui.App;
import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.BuildingType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.BuildBase;
import com.pps.asciigame.common.protocol.BuildBuilding;
import com.pps.asciigame.common.protocol.Login;
import com.pps.asciigame.common.protocol.RequestBasicInfo;
import com.pps.asciigame.common.util.BuildingFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.Socket;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        App.main(args);
    }

    //UNUSED - left for reference
    private void srart() {
        final var user = new User("someUser");  //todo get username from UI
        try (final var socket = new Socket(Config.hostname(), Config.port())) {
            final var connection = new Connection(socket, null);
            connection.setUser(user);
//            Thread.sleep(5000);
            connection.write(new Login(user));

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

            final var building = BuildingFactory.createBuilding(base, BuildingType.WATCHTOWER);
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
