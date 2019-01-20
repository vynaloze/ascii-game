package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final User user;
    private final boolean broadcast;

    public Message(final User user) {
        this.user = user;
        this.broadcast = false;
    }

    protected Message(final User user, final boolean broadcast) {
        this.user = user;
        this.broadcast = broadcast;
    }

    public User getUser() {
        return user;
    }

    public boolean isBroadcast() {
        return broadcast;
    }
}
