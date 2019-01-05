package com.pps.asciigame.ws.game.bases;

import com.pps.asciigame.common.JsonParser;
import com.pps.asciigame.common.model.*;
import com.pps.asciigame.common.util.BuildingFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseService {
    @Autowired
    private BaseRepository baseRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    public void addBase(final Base base) {
        baseRepository.save(base);
        final var centralBuilding = BuildingFactory.createBuilding(base, BuildingType.CENTRAL);
        buildingRepository.save(centralBuilding);
    }

    public void addBuilding(final Building building) {
        buildingRepository.save(building);
    }

    public List<Base> getBasesWithOwner(final User owner) {
        return baseRepository.findByOwner(owner);
    }

    public List<Building> getBuildingsInBase(final Base base) {
        return buildingRepository.findByBase(base);
    }

    public List<Building> getBuildingsByOwner(final User owner) {
        return getBasesWithOwner(owner).stream()
                .map(this::getBuildingsInBase)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public double getTotalProfitOf(final ResourceType resourceType, final User user) {
        return getBasesWithOwner(user).stream()
                .map(this::getBuildingsInBase)
                .flatMap(Collection::stream)
                .map(building -> JsonParser.asObject(building.getProfits(), ResourceAmounts.class))
                .mapToDouble(profits -> profits.getAmount(resourceType))
                .sum();
    }

}
