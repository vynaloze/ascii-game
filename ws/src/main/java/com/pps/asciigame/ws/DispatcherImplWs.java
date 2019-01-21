package com.pps.asciigame.ws;

import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.protocol.*;
import com.pps.asciigame.ws.chat.ChatController;
import com.pps.asciigame.ws.game.web.BaseController;
import com.pps.asciigame.ws.game.web.LoginController;
import com.pps.asciigame.ws.game.web.OperationsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DispatcherImplWs implements Dispatcher {
    @Autowired
    private ChatController chatController;
    @Autowired
    private LoginController loginController;
    @Autowired
    private BaseController baseController;
    @Autowired
    private OperationsController operationController;

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(final Message message) {
        if (message instanceof ChatEntry) {
            chatController.handle((ChatEntry) message);
        } else if (message instanceof Login) {
            loginController.registerNewUserIfNeeded((Login) message); //todo - if we change this flow, this is the place to change
        } else if (message instanceof RequestBasicInfo) {
            baseController.provideBasicInfo(message.getUser());
        } else if (message instanceof BuildBase) {
            baseController.addBase(((BuildBase) message).getBase());
        } else if (message instanceof BuildBuilding) {
            baseController.addBuilding(((BuildBuilding) message).getBuilding());
        } else if (message instanceof PerformOperation) {
        	operationController.performOperation(((PerformOperation) message).getOperation(), message.getUser());
        } else if (message instanceof RequestMapData) {
            baseController.sendAllBases(message.getUser());
        } else {
            throw new UnsupportedOperationException("Unsupported message type.");
        }
    }
}
