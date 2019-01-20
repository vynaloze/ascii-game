package com.pps.asciigame.ws.game.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.ws.game.bases.BaseService;

public class OperationsController {
	
	@Autowired
    private BaseService baseService;
	
    public void performOperation(final Operation operation) {
    	final var base = operation.getHomeBase();
    	final List<Building> buildings = baseService.getBuildingsInBase(base);
    	if(buildings.contains(operation.getRequiredBuilding())){    		
    		if(calculateRange(operation) <= operation.getOperationType().getRange()){
    			// todo - what should it do depending on the operation
    		}
    	}
    }
    
    public int calculateRange(final Operation operation)
    {
    	return Math.abs(operation.getHomeBase().getX() - operation.getTargetBase().getX()) + Math.abs(operation.getHomeBase().getY() - operation.getTargetBase().getY());
    }
}
