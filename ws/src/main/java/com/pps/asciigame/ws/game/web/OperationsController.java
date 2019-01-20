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
    	if(buildings.contains(operation.getRequiredBuildings())){
    		// todo - what should it do depending on the operation
    	}

    }

}
