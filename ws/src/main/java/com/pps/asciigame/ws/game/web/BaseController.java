package com.pps.asciigame.ws.game.web;

import com.pps.asciigame.common.JsonParser;
import com.pps.asciigame.common.model.*;
import com.pps.asciigame.common.protocol.BasicInfo;
import com.pps.asciigame.common.protocol.Confirmation;
import com.pps.asciigame.common.protocol.MapData;
import com.pps.asciigame.ws.ConnectionManager;
import com.pps.asciigame.ws.game.bases.BaseService;
import com.pps.asciigame.ws.game.resources.ResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BaseController {
    private static final Logger LOGGER = LogManager.getLogger(BaseController.class);
    @Autowired
    private ConnectionManager connectionManager;
    @Autowired
    private BaseService baseService;
    @Autowired
    private ResourceService resourceService;

    public void provideBasicInfo(final User user) {
        final List<Resource> resources = updateAndGetAmounts(user);
        final List<Base> bases = baseService.getBasesWithOwner(user);
        final List<Building> buildings = baseService.getBuildingsByOwner(user);
        final var basicInfo = new BasicInfo(user, resources, bases, buildings);
        connectionManager.pushTo(user, basicInfo);
    }

    public void addBase(final Base base, User user) {
    	if(!baseService.getAllBases().contains(base)) {
            if (baseService.isAdjacentToFriendly(base) || baseService.getBasesWithOwner(base.getOwner()).size() == 0) {
    			baseService.addBase(base);
    			provideBasicInfo(base.getOwner());
    			connectionManager.pushTo(user, new Confirmation(user, "You successfully built a base!"));
    		}
            else {
            connectionManager.pushTo(user, new Confirmation(user, "Failed to build a base - not adjacent to a friendly base!"));
            }
    	}
    	else {
    		connectionManager.pushTo(user, new Confirmation(user, "Failed to build a base - there's already a base in that space!"));
    	}
    }

    public void addBuilding(final Building building, User user2) {
        final var user = building.getBase().getOwner();
        final var resources = resourceService.getAll(user);
        final var costs = JsonParser.asObject(building.getCosts(), ResourceAmounts.class);
        if (isEnoughResources(resources, costs)) {
            updateResources(resources, costs);
            baseService.addBuilding(building);
            provideBasicInfo(user);
            connectionManager.pushTo(user, new Confirmation(user, "You successfully built a building!"));
        }
        else {
        	connectionManager.pushTo(user, new Confirmation(user, "Failed to build a building - not enough resources!"));
        }
    }

    public void sendAllBases(final User user) {
        final var bases = baseService.getAllBases();
        connectionManager.pushTo(user, new MapData(user, bases));
    }

    private List<Resource> updateAndGetAmounts(final User user) {
        final var resources = new ArrayList<Resource>();
        for (final var resourceType : ResourceType.values()) {
            final var initalResource = resourceService.getByType(user, resourceType);
            final var profit = baseService.getTotalProfitOf(resourceType, user);
            resources.add(resourceService.updateAndGet(initalResource, profit));
        }
        return resources;
    }

    private boolean isEnoughResources(final List<Resource> resources, final ResourceAmounts costs) {
        return resources.stream().noneMatch(resource -> resource.getAmount() < costs.getAmount(resource.getType()));
    }

    private void updateResources(final List<Resource> resources, final ResourceAmounts costs) {
        resources.forEach(resource -> {
            resource.setAmount(resource.getAmount() - costs.getAmount(resource.getType()));
            resourceService.update(resource);
        });
    }
}
