package com.pps.asciigame.ws.game;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.BuildingType;
import com.pps.asciigame.common.model.ResourceType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.util.BuildingFactory;
import com.pps.asciigame.ws.Main;
import com.pps.asciigame.ws.game.bases.BaseRepository;
import com.pps.asciigame.ws.game.bases.BaseService;
import com.pps.asciigame.ws.game.users.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class BaseServiceTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseServiceTest.class);
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserRepository userRepository;

    private final User user = new User("testuser");

    @Before
    public void setUp() {
        userRepository.save(user);
    }

    @Test
    public void shouldAddBaseWithCentralBuilding() {
        //given
        final var base = new Base(0, 0, "testbase", user);
        //when
        baseService.addBase(base);
        //then
        assertThat(baseService.getBasesWithOwner(user)).containsOnly(base);
        assertThat(baseService.getBuildingsInBase(base)).hasSize(1).allMatch(b -> b.getType().equals(BuildingType.CENTRAL));
    }

    @Test
    public void shouldAddAnotherBuilding() {
        //given
        final var base = new Base(0, 0, "testbase", user);
        final var building = BuildingFactory.createBuilding(base, BuildingType.A);
        baseService.addBase(base);
        //when
        baseService.addBuilding(building);
        //then
        assertThat(baseService.getBuildingsInBase(base)).hasSize(2).contains(building);
    }
    
    @Test
    public void shouldReturnAllBases() {
        //given
        final var base = new Base(0, 0, "testbase", user);
        final var base2 = new Base(0, 0, "testbase2", user);
        //when
        baseService.addBase(base);
        baseService.addBase(base2);
        //then
        assertThat(baseService.getAllBases().size() == 2);        
        assertThat(baseService.getAllBases().contains(base));
        assertThat(baseService.getAllBases().contains(base2));
    }
    
    @Test
    public void shouldRemoveBase() {
        //given
        final var base = new Base(0, 0, "testbase", user);
        //when
        baseService.addBase(base);
        baseService.removeBase(base);
        //then
        assertFalse(baseService.getAllBases().contains(base));
    }

    @Test
    public void shouldCalculateTotalProfit() {
        //given
        final var base = new Base(0, 0, "testbase", user);
        final var building = BuildingFactory.createBuilding(base, BuildingType.A);
        baseService.addBase(base);
        baseService.addBuilding(building);
        final var expectedProfit = BuildingType.CENTRAL.getProfit().getAmount(ResourceType.MINERAL)
                + BuildingType.A.getProfit().getAmount(ResourceType.MINERAL);
        //when
        final var totalProfit = baseService.getTotalProfitOf(ResourceType.MINERAL, user);
        //then
        assertThat(totalProfit).isEqualTo(expectedProfit);
    }
}
