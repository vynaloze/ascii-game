package com.pps.asciigame.ws.game.bases;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Building;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuildingRepository extends CrudRepository<Building, Long> {
    List<Building> findByBase(Base base);
}
