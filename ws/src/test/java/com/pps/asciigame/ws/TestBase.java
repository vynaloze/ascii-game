package com.pps.asciigame.ws;

import com.pps.asciigame.common.messages.ChatEntry;

import java.time.LocalDateTime;

public abstract class TestBase {
    protected final ChatEntry chatEntry = new ChatEntry(LocalDateTime.now(), "Author", "Message");
}
