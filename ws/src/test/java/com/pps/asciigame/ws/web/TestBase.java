package com.pps.asciigame.ws.web;

import com.pps.asciigame.common.protocol.ChatEntry;

import java.time.LocalDateTime;

public abstract class TestBase {
    protected final ChatEntry chatEntry = new ChatEntry(LocalDateTime.now(), "Author", "Message");
}
