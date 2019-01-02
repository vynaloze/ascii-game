package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.User;

import java.util.List;

public class BasicInfo extends Message {
    private final List<Resource> resources;
    private final List<Base> bases;
    private final List<Building> buildings;

    public BasicInfo(User user, List<Resource> resources, List<Base> bases, List<Building> buildings) {
        super(user);
        this.resources = resources;
        this.bases = bases;
        this.buildings = buildings;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public List<Base> getBases() {
        return bases;
    }

    public List<Building> getBuildings() {
        return buildings;
    }
}
