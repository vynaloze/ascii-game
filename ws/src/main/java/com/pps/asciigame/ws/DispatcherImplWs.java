package com.pps.asciigame.ws;

import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.Login;
import com.pps.asciigame.common.protocol.Message;
import com.pps.asciigame.ws.chat.ChatController;
import com.pps.asciigame.ws.game.web.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DispatcherImplWs implements Dispatcher {
    @Autowired
    private ChatController chatController;
    @Autowired
    private LoginController loginController;

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(final Message message) {
        if (message instanceof ChatEntry) {
            chatController.handle((ChatEntry) message);
        } else if (message instanceof Login) {
            loginController.registerNewUserIfNeeded((Login) message); //todo - if we change this flow, this is the place to change
        } else {
            throw new UnsupportedOperationException("Unsupported message type.");
        }
    }
}
