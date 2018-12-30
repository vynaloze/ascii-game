package com.pps.asciigame.ws.chat;

import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.ws.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatController {
    private static final Logger LOGGER = LogManager.getLogger(ChatController.class);
    @Autowired
    private ConnectionManager connectionManager;

    public void handle(final ChatEntry chatEntry) {
        LOGGER.info("Client sent chatEntry: " + chatEntry);
        connectionManager.pushToAll(chatEntry);
    }
}
