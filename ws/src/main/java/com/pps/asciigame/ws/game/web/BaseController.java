package com.pps.asciigame.ws.game.web;

import com.pps.asciigame.common.JsonParser;
import com.pps.asciigame.common.model.*;
import com.pps.asciigame.common.protocol.BasicInfo;
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

    //todo - it should be PROBABLY pushed periodically to client - without need to request
    public void provideBasicInfo(final User user) {
        final List<Resource> resources = updateAndGetAmounts(user);
        final List<Base> bases = baseService.getBasesWithOwner(user);
        final List<Building> buildings = baseService.getBuildingsByOwner(user);
        final var basicInfo = new BasicInfo(user, resources, bases, buildings);
        connectionManager.pushTo(user, basicInfo);
    }

    public void addBase(final Base base) {
        baseService.addBase(base); //todo when it is possible to add new base?
        provideBasicInfo(base.getOwner());
    }

    public void addBuilding(final Building building) {
        final var user = building.getBase().getOwner();
        final var resources = resourceService.getAll(user);
        final var costs = JsonParser.asObject(building.getCosts(), ResourceAmounts.class);
        if (isEnoughResources(resources, costs)) {
            updateResources(resources, costs);
            baseService.addBuilding(building);
            provideBasicInfo(user);
        }
        //todo give some feedback on result
        //todo test for this controller
        //todo fix todos :)
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
