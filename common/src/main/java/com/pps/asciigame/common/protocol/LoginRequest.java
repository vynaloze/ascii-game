package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

public class LoginRequest extends Message {
    public LoginRequest(final User user) {
        super(user);
    }
}
