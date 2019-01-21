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
    
    public void removeBuilding(final Building building) {
    	buildingRepository.delete(building);
    }
    
    public Building getRandomBuilding(final Base base) {
    	final var buildings = getBuildingsInBase(base);
    	final int index = (int) Math.random() * (buildings.size() - 1);
    	return buildings.get(index);
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
    
    public List<Base> getAllBases() {
        return (List<Base>) baseRepository.findAll();
    }
    
    public boolean isAdjacentToFriendly(final Base base) {
    	for(Base iterator : getBasesWithOwner(base.getOwner())) {
    		if(iterator.getX()+1 == base.getX() && iterator.getY() == base.getY()) {
    			return true;
    		}
    		if(iterator.getX()-1 == base.getX() && iterator.getY() == base.getY()) {
    			return true;
    		}
    		if(iterator.getX() == base.getX() && iterator.getY()-1 == base.getY()) {
    			return true;
    		}
    		if(iterator.getX() == base.getX() && iterator.getY()+1 == base.getY()) {
    			return true;
    		}
    	}  	
    	return false;
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
