package com.pps.asciigame.ws.web;

import com.pps.asciigame.common.protocol.Message;
import com.pps.asciigame.ws.DispatcherImplWs;
import com.pps.asciigame.ws.chat.ChatController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherTest extends AbstractTestData {
    @Mock
    private ChatController chatController;
    @InjectMocks
    private DispatcherImplWs dispatcher;

    @Test
    public void shouldParseChatEntryMessage() { // kinda stupid test, but who cares. Let's do more of them!
        //when
        dispatcher.dispatch(chatEntry);
        //then
        verify(chatController).handle(chatEntry);
    }

    @Test
    public void shouldThrowExceptionOtherwise() {
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(() -> dispatcher.dispatch(new Message(testUser) {
        }));
    }
}
