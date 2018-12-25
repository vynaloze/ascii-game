package com.pps.asciigame.client.chat;

import com.pps.asciigame.common.messages.ChatEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ChatController {
    private static final Logger LOGGER = LogManager.getLogger(ChatController.class);

    public void handle(final ChatEntry chatEntry) {
        LOGGER.info("Sever sent chatEntry: " + chatEntry);
    }
}
