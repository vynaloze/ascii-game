package com.pps.asciigame.ws.web;

import com.pps.asciigame.ws.ConnectionManager;
import com.pps.asciigame.ws.game.bases.BaseService;
import com.pps.asciigame.ws.game.resources.ResourceService;
import com.pps.asciigame.ws.game.web.BaseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BaseControllerTest extends AbstractTestData {
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private BaseService baseService;
    @Mock
    private ResourceService resourceService;
    @InjectMocks
    private BaseController baseController;

    @Test
    public void shouldAddBase() {
        //given

        //when

        //then

    }
}
