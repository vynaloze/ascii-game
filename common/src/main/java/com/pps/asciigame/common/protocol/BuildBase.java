package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.User;

public class BuildBase extends Message {
    private final Base base;

    public BuildBase(User user, Base base) {
        super(user);
        this.base = base;
    }

    public Base getBase() {
        return base;
    }
}
