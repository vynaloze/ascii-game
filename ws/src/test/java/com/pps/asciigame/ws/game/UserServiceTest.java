package com.pps.asciigame.ws.game;

import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.DuplicateUserFoundException;
import com.pps.asciigame.common.model.exception.UserNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
        final var name = "testuser";
        final var user = new User(name);
        //when
        userService.addUser(user);
        //then
        assertThat(userService.getUserByName(name)).isEqualTo(user);
    }

    @Test
    public void shouldThrowExceptionOnNotPresentUser() {
        //given
        final var name = "testuser";
        final var wrongName = "testuserr";
        final var user = new User(name);
        userService.addUser(user);
        //then
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> userService.getUserByName(wrongName));
    }

    @Test
    public void shouldThrowExceptionOnDuplicateUsername() {
        //given
        final var name = "testuser";
        final var user = new User(name);
        userService.addUser(user);
        final var duplicateUser = new User(name);
        //then
        assertThatExceptionOfType(DuplicateUserFoundException.class).isThrownBy(() -> userService.addUser(duplicateUser));
    }


}
