package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private User user;

    public LoginRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
