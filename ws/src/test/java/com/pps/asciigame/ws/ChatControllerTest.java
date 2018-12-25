package com.pps.asciigame.ws;

import com.pps.asciigame.ws.chat.ChatController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest extends TestBase {
    @Mock
    private ConnectionManager connectionManager;
    @InjectMocks
    private ChatController chatController;

    @Test
    public void shouldPushMessageToAllClients() {
        //when
        chatController.handle(chatEntry);
        //then
        verify(connectionManager).pushToAll(chatEntry);
    }

}
