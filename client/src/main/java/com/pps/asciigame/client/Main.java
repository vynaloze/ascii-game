package com.pps.asciigame.client;

import com.pps.asciigame.common.Connection;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.messages.ChatEntry;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) { //FIXME - for quick verification only!
        try (var socket = new Socket("localhost", 8080)) {
            Dispatcher dispatcher = new DispatcherImplClient();
            var connection = new Connection(socket, dispatcher);

            var chatEntry = new ChatEntry(LocalDateTime.now(), "Author", "MEssage");
            connection.write(chatEntry);
            Thread.sleep(5000);

            var chatEntry2 = new ChatEntry(LocalDateTime.now(), "Author", "MEssage 2");
            connection.write(chatEntry2);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
