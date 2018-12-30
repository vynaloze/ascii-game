package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final User user;

    protected Message(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
