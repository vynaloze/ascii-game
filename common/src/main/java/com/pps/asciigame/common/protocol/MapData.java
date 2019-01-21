package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.User;

import java.util.List;

public class MapData extends Message {
    private final List<Base> bases;

    public MapData(User user, List<Base> bases) {
        super(user);
        this.bases = bases;
    }

    public List<Base> getBases() {
        return bases;
    }
}
