package com.pps.asciigame.ws.web;

import com.pps.asciigame.common.protocol.BuildBase;
import com.pps.asciigame.common.protocol.Message;
import com.pps.asciigame.common.protocol.PerformOperation;
import com.pps.asciigame.ws.DispatcherImplWs;
import com.pps.asciigame.ws.chat.ChatController;
import com.pps.asciigame.ws.game.web.BaseController;
import com.pps.asciigame.ws.game.web.OperationsController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherTest extends TestBase {
    @Mock
    private ChatController chatController;
    @Mock
    private BaseController baseController;
    @Mock
    private OperationsController operationController;
    @InjectMocks
    private DispatcherImplWs dispatcher;

    @Test
    public void shouldParseChatEntryMessage() { 
        //when
        dispatcher.dispatch(chatEntry);
        //then
        verify(chatController).handle(chatEntry);
    }
    
    @Test
    public void shouldParsePerformOperationMessage() { 
        //when
        dispatcher.dispatch(performOperation);
        //then
        verify(operationController).performOperation(testOperation, testUser);
    }
    
    @Test
    public void shouldParseBuildBuildingMessage() { 
        //when
        dispatcher.dispatch(buildBuilding);
        //then
        verify(baseController).addBuilding((buildBuilding).getBuilding(), buildBuilding.getUser());
    }
    
    @Test
    public void shouldParseBuildBaseMessage() { 
        //when
        dispatcher.dispatch(buildBase);
        //then
        verify(baseController).addBase(((BuildBase) buildBase).getBase(), buildBase.getUser());
    }

    @Test
    public void shouldThrowExceptionOtherwise() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> dispatcher.dispatch(new Message(testUser) {
        }));
    }
}
