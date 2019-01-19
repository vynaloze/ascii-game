package com.pps.asciigame.client.chat;

import com.pps.asciigame.client.ui.MainFXMLController;
import com.pps.asciigame.common.protocol.ChatEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatController {
    private static final Logger LOGGER = LogManager.getLogger(ChatController.class);
    @Autowired
    private MainFXMLController mainFXMLController;

    public void handle(final ChatEntry chatEntry) {
        LOGGER.info("Sever sent chatEntry: " + chatEntry);
        mainFXMLController.updateChatHistory(chatEntry);
    }
}
