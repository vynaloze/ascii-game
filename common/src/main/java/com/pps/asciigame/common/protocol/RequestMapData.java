package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.User;

public class RequestMapData extends Message {
    public RequestMapData(final User user) {
        super(user);
    }
}
