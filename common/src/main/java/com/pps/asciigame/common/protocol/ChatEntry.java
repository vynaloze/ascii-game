package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatEntry extends Message {
    private static final transient DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
    private final LocalDateTime dateTime;
    private final String message;

    public ChatEntry(final LocalDateTime dateTime, final User author, final String message) {
        super(author, true);
        this.dateTime = dateTime;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return dateTime.format(FORMATTER) + " <" + getUser().getName() + "> " + message;
    }
}
