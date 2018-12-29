package com.pps.asciigame.ws.game.bases;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.BuildingType;
import com.pps.asciigame.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
