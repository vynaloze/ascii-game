package com.pps.asciigame.ws.game.users;

import com.pps.asciigame.common.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(final String name);
}
