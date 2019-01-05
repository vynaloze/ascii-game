package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.User;

public class BuildBuilding extends Message {
    private final Building building;

    public BuildBuilding(User user, Building building) {
        super(user);
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }
}
