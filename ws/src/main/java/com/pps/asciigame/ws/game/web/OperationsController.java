package com.pps.asciigame.ws.game.web;

import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.Confirmation;
import com.pps.asciigame.ws.ConnectionManager;
import com.pps.asciigame.ws.game.bases.BaseService;
import com.pps.asciigame.ws.game.resources.ResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationsController {
    private static final Logger LOGGER = LogManager.getLogger(OperationsController.class);
	@Autowired
    private BaseService baseService;
	@Autowired
	private ResourceService resourceService;
    @Autowired
    private ConnectionManager connectionManager;
	
    public void performOperation(final Operation operation, User user) {
    	final var base = operation.getHomeBase();
    	final List<Building> buildings = baseService.getBuildingsInBase(base);  		
		if(calculateRange(operation) <= operation.getOperationType().getRange()){
			if(operation.getOperationType().getEffect().equals("steal")) {
                LOGGER.info(user.getName() + " tries to steal resources from " + operation.getTargetBase().getOwner().getName());
                stealResources(operation, user);
			} else if (operation.getOperationType().getEffect().equals("burn")) {
                LOGGER.info(user.getName() + " tries to burn sth from " + operation.getTargetBase().getOwner().getName());
                burnBuilding(operation, user);
			}
		}
		else
		{
			connectionManager.pushTo(user, new Confirmation(user, "Failure"));
		}
    }
    
    public void stealResources(final Operation operation, User user) {
    	final var resourcesTarget = resourceService.getAll(operation.getTargetBase().getOwner());
//    	final var resourcesHome = resourceService.getAll(operation.getHomeBase().getOwner()); fixme - workaround to not use HomeBase at all (see PerformOperationFXMLController)
        final var resourcesHome = resourceService.getAll(user);
    	resourcesHome.forEach(resource -> {
            resource.setAmount(resource.getAmount() + resource.getAmount() * operation.getOperationType().getPercent()); //todo - how to add percent of resources from different player?
            resourceService.update(resource);
        });
    	
        resourcesTarget.forEach(resource -> {
            resource.setAmount(resource.getAmount() - resource.getAmount() * operation.getOperationType().getPercent());
            resourceService.update(resource);
        });

        connectionManager.pushTo(user, new Confirmation(user, "Success")); // thief
        final var victim = operation.getTargetBase().getOwner();
        connectionManager.pushTo(victim, new Confirmation(victim, "U have been robbed.")); //todo better all these confirmation messages, please
        
    }
    
    public void burnBuilding(final Operation operation, User user) {
    	final double roll = Math.random();
    	if(roll < operation.getOperationType().getPercent()) {
    		final var building = baseService.getRandomBuilding(operation.getTargetBase());
        	baseService.removeBuilding(building);        	
        	if(baseService.getBuildingsInBase(operation.getTargetBase()).isEmpty())
        	{
        		baseService.removeBase(operation.getTargetBase());
        	}
        	connectionManager.pushTo(user, new Confirmation(user, "Success"));
            final var victim = operation.getTargetBase().getOwner();
            connectionManager.pushTo(victim, new Confirmation(victim, "U have been burned [from ???]"));
    	}
    	else
    	{
    		connectionManager.pushTo(user, new Confirmation(user, "Failure"));
            final var victim = operation.getTargetBase().getOwner();
            connectionManager.pushTo(victim, new Confirmation(victim, "U managed to escape fire from"));
    	}
    }
    
    public int calculateRange(final Operation operation)
    {
        //fixme - workaround to not use HomeBase at all (see PerformOperationFXMLController)
//    	return Math.abs(operation.getHomeBase().getX() - operation.getTargetBase().getX()) + Math.abs(operation.getHomeBase().getY() - operation.getTargetBase().getY());
        return 0;
    }
}
