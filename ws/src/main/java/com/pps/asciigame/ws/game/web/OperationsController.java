package com.pps.asciigame.ws.game.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.protocol.MapData;
import com.pps.asciigame.ws.ConnectionManager;
import com.pps.asciigame.ws.game.bases.BaseService;
import com.pps.asciigame.ws.game.resources.ResourceService;

@Component
public class OperationsController {
	
	@Autowired
    private BaseService baseService;
	@Autowired
	private ResourceService resourceService;
    @Autowired
    private ConnectionManager connectionManager;
	
    public void performOperation(final Operation operation) {
    	final var base = operation.getHomeBase();
    	final List<Building> buildings = baseService.getBuildingsInBase(base);  		
		if(calculateRange(operation) <= operation.getOperationType().getRange()){
			if(operation.getOperationType().getEffect().equals("steal")) {
				stealResources(operation);
			}
			else if (operation.getOperationType().getEffect().equals("burn")) { 
				burnBuilding(operation);
			}
		}
		else
		{
			//operation failed
			//connectionManager.pushTo(user, new MapData(user, bases));
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
    	final double roll = Math.random();
    	if(roll < operation.getOperationType().getPercent()) {
    		final var building = baseService.getRandomBuilding(operation.getTargetBase());
        	baseService.removeBuilding(building);        	
        	if(baseService.getBuildingsInBase(operation.getTargetBase()).isEmpty())
        	{
        		baseService.removeBase(operation.getTargetBase());
        	}
    	}
    	else
    	{
    		
    	}
    }
    
    public int calculateRange(final Operation operation)
    {
    	return Math.abs(operation.getHomeBase().getX() - operation.getTargetBase().getX()) + Math.abs(operation.getHomeBase().getY() - operation.getTargetBase().getY());
    }
}
