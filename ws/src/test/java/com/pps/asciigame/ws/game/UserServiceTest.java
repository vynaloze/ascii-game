package com.pps.asciigame.ws.game;

import com.pps.asciigame.common.model.User;
import com.pps.asciigame.ws.Main;
import com.pps.asciigame.ws.game.users.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceTest.class);
    @Autowired
    private UserService userService;

    @Test
    public void shouldAddUser() {
        //given
        final String name = "testuser";
        final User user = new User(name);
        //when
        userService.addUser(user);
        //then
        assertThat(userService.getUserByName(name)).hasSize(1).containsOnly(user);
    }


}
