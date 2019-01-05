package com.pps.asciigame.ws.game.web;

import com.pps.asciigame.common.protocol.Login;
import com.pps.asciigame.ws.game.resources.ResourceService;
import com.pps.asciigame.ws.game.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    public void registerNewUserIfNeeded(final Login loginMessage) {
        final var user = loginMessage.getUser();
        if (userService.getUserByName(user.getName()).isEmpty()) {
            userService.addUser(user);
            resourceService.initResourcesForUser(user);
        }
    }

}
