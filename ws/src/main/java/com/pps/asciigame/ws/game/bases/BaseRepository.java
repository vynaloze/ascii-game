package com.pps.asciigame.ws.game.bases;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BaseRepository extends CrudRepository<Base, Long> {
    List<Base> findByOwner(User owner);
}
