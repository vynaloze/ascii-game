package com.pps.asciigame.ws.game.users;

import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.DuplicateUserFoundException;
import com.pps.asciigame.common.model.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User getUserByName(final String name) {
        return userRepository.findByName(name).orElseThrow(UserNotFoundException::new);
    }
}
