package com.pps.asciigame.ws.game.users;

import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.DuplicateUserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(final User user) {
        if (userRepository.findByName(user.getName()).isEmpty()) {
            userRepository.save(user);
        } else {
            throw new DuplicateUserFoundException();
        }
    }

    public Optional<User> getUserByName(final String name) {
        return userRepository.findByName(name);
    }
}
