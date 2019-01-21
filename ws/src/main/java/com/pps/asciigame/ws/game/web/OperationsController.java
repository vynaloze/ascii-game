package com.pps.asciigame.ws.game.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.ResourceAmounts;
import com.pps.asciigame.ws.game.bases.BaseService;
import com.pps.asciigame.ws.game.resources.ResourceService;

public class OperationsController {
	
	@Autowired
    private BaseService baseService;
	@Autowired
	private ResourceService resourceService;
	
    public void performOperation(final Operation operation) {
    	final var base = operation.getHomeBase();
    	final List<Building> buildings = baseService.getBuildingsInBase(base);
    	if(buildings.contains(operation.getRequiredBuilding())){    		
    		if(calculateRange(operation) <= operation.getOperationType().getRange()){
    			// todo - what should it do depending on the operation
    		}
    	}
    }
    
    public void stealResources(final Operation operation) {
    	final var resourcesTarget = resourceService.getAll(operation.getTargetBase().getOwner());
    	final var resourcesHome = resourceService.getAll(operation.getHomeBase().getOwner());
    	
    	resourcesHome.forEach(resource -> {
            resource.setAmount(resource.getAmount() + resource.getAmount() * operation.getOperationType().getPercent()); //todo - how to add percent of resources from different player?
            resourceService.update(resource);
        });
    	
        resourcesTarget.forEach(resource -> {
            resource.setAmount(resource.getAmount() - resource.getAmount() * operation.getOperationType().getPercent());
            resourceService.update(resource);
        });
        
        
    }
    
    public void burnBuilding(final Operation operation) {
    	
    }
    
    public int calculateRange(final Operation operation)
    {
    	return Math.abs(operation.getHomeBase().getX() - operation.getTargetBase().getX()) + Math.abs(operation.getHomeBase().getY() - operation.getTargetBase().getY());
    }
}
