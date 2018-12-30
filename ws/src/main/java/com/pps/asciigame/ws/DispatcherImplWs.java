package com.pps.asciigame.ws;

import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.ws.chat.ChatController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DispatcherImplWs implements Dispatcher {
    @Autowired
    private ChatController chatController;

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(final Object message) {
        if (message instanceof ChatEntry) {
            chatController.handle((ChatEntry) message);
//        } else if (message instanceof LoginRequest) {

        } else {
            throw new UnsupportedOperationException("Unsupported message type.");
        }
    }
}
