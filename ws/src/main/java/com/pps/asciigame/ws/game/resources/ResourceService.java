package com.pps.asciigame.ws.game.resources;

import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.ResourceType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.NegativeResourceAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Value("${asciigame.resources.update_period}")
    private int updatePeriod;

    public void initResourcesForUser(final User user) {
        for (final var type : ResourceType.values()) {
            final var resource = new Resource(user, type.getIntialAmount(), type, LocalDateTime.now());
            resourceRepository.save(resource);
        }
    }

    public List<Resource> getAll(final User user) {
        return resourceRepository.findByUser(user);
    }

    public Resource getByType(final User user, final ResourceType resourceType) {
        return resourceRepository.findByUserAndType(user, resourceType).get(); //todo orelsethrow
    }

    public void update(final Resource resource) {
        if (resource.getAmount() < 0.0) {
            throw new NegativeResourceAmountException();
        }
        resourceRepository.save(resource);
    }

    public synchronized Resource updateAndGet(final Resource resource, final double profit) { //fixme synchronized is shit. or screw it?
        final long secondsFromLastUpdate = resource.getLastUpdated().until(LocalDateTime.now(), ChronoUnit.SECONDS);
        final long fullUpdatePeriods = secondsFromLastUpdate / updatePeriod;
        resource.setAmount(resource.getAmount() + profit * fullUpdatePeriods);
        resource.setLastUpdated(resource.getLastUpdated().plusSeconds(fullUpdatePeriods * updatePeriod));
        return resourceRepository.save(resource);
    }

}
