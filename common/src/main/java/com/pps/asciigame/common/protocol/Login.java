package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

public class Login extends Message {
    public Login(final User user) {
        super(user);
    }
}
