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

    @Override
    public String toString() {
        final var sb = new StringBuilder("BasicInfo:");
        for (final var resource : resources) {
            sb.append(" ").append(resource.getType()).append("=").append(resource.getAmount());
        }
        sb.append("; BASES:");
        for (final var base : bases) {
            sb.append(" ").append(base.getName());
        }
        sb.append("; BUILDINGS:");
        for (final var building : buildings) {
            sb.append(" ").append(building.getType());
        }
        return sb.toString();
    }
}
