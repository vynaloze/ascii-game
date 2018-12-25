package com.pps.asciigame.ws.chat;

import com.pps.asciigame.common.messages.ChatEntry;
import org.springframework.stereotype.Component;

@Component
public class ChatController {

    public void handle(final ChatEntry chatEntry) {
        System.out.println(chatEntry);
    }
}
