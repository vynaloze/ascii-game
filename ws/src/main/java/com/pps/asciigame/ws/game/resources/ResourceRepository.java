package com.pps.asciigame.ws.game.resources;

import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.ResourceType;
import com.pps.asciigame.common.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends CrudRepository<Resource, Long> {
    List<Resource> findResourceByUser(User user);

    Optional<Resource> findResourceByUserAndType(User user, ResourceType resourceType);
}
