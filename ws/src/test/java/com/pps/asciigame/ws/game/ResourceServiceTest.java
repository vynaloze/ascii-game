package com.pps.asciigame.ws.game;

import com.pps.asciigame.common.model.Resource;
import com.pps.asciigame.common.model.ResourceType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.exception.NegativeResourceAmountException;
import com.pps.asciigame.ws.Main;
import com.pps.asciigame.ws.game.resources.ResourceService;
import com.pps.asciigame.ws.game.users.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class ResourceServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(ResourceServiceTest.class);
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserRepository userRepository;
    @Value("${asciigame.resources.update_period}")
    private int updatePeriod;

    private final User user = new User("testuser");

    @Before
    public void setUp() {
        userRepository.save(user);
        resourceService.initResourcesForUser(user);
    }

    @Test
    public void shouldInitResources() {
        assertThat(resourceService.getAll(user)).hasSize(3);
    }

    @Test
    public void shouldUpdateAmount() {
        //given
        final var newAmount = 1000.0;
        final var resource = resourceService.getByType(user, ResourceType.GOLD);
        resource.setAmount(newAmount);
        //when
        resourceService.update(resource);
        //then
        assertThat(resourceService.getByType(user, ResourceType.GOLD).getAmount()).isEqualTo(newAmount);
    }

    @Test
    public void shouldNotUpdateNegativeAmount() {
        //given
        final var newAmount = -1.0;
        final var resource = resourceService.getByType(user, ResourceType.FOOD);
        resource.setAmount(newAmount);
        //then
        assertThatExceptionOfType(NegativeResourceAmountException.class).isThrownBy(() -> resourceService.update(resource));
    }

    @Test
    public void shouldUpdateAndGet() throws Exception {
        //given
        final var resource = resourceService.getByType(user, ResourceType.FOOD);
        final double profit = 3.0;
        final int waitingPeriod = updatePeriod * 3;
        final double expectedAmount = resource.getAmount() + waitingPeriod * profit;
        //when
        Thread.sleep(waitingPeriod * 1000);
        final Resource result = resourceService.updateAndGet(resource, profit);
        //then
        assertThat(result.getAmount()).isEqualTo(expectedAmount);
    }

}
