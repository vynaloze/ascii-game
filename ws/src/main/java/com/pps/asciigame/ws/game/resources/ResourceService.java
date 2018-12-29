package com.pps.asciigame.ws.game.resources;

import com.pps.asciigame.common.configuration.Config;
import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.ResourceType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.NegativeResourceAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public void initResourcesForUser(final User user) {
        Config.baseResources().forEach((type, amount) -> {
            final var resource = new Resource(user, amount.doubleValue(), type, LocalDateTime.now());
            resourceRepository.save(resource);
        });
    }

    public List<Resource> getResourcesOfUser(final User user) {
        return resourceRepository.findByUser(user);
    }

    public Resource getResourceOfUser(final User user, final ResourceType resourceType) {
        return resourceRepository.findByUserAndType(user, resourceType).get(); //todo orelsethrow
    }

    public void updateResource(final Resource resource) {
        if (resource.getAmount() < 0.0) {
            throw new NegativeResourceAmountException();
        }
        resourceRepository.save(resource);
    }

}
