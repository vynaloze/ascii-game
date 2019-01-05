package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

public class RequestBasicInfo extends Message {
    public RequestBasicInfo(final User user) {
        super(user);
    }
}
