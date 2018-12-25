package com.pps.asciigame.common.messages;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatEntry implements Serializable {
    private static final transient DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
    private LocalDateTime dateTime;
    private String author;
    private String message;

    public ChatEntry(final LocalDateTime dateTime, final String author, final String message) {
        this.dateTime = dateTime;
        this.author = author;
        this.message = message;
    }

    public ChatEntry() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return dateTime.format(FORMATTER) + " <" + author + "> " + message;
    }
}
