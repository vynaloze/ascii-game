package com.pps.asciigame.ws.game.users;

import com.pps.asciigame.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(final User user) {
        userRepository.save(user);
    }

    public List<User> getUserByName(final String name) {
        return userRepository.findByName(name);
    }
}
