package com.pps.asciigame.client;

import com.pps.asciigame.client.chat.ChatController;
import com.pps.asciigame.client.game.DataReceivingController;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.protocol.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DispatcherImplClient implements Dispatcher {
    @Autowired
    private ChatController chatController;
    @Autowired
    private DataReceivingController dataReceivingController;

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(final Message message) {
        if (message instanceof ChatEntry) {
            chatController.handle((ChatEntry) message);
        } else if (message instanceof BasicInfo) {
            dataReceivingController.updateBasicInfo((BasicInfo) message);
        } else if (message instanceof MapData) {
            dataReceivingController.updateMapData((MapData) message);
        } else if (message instanceof Confirmation) {
            dataReceivingController.showConfirmation((Confirmation) message);
        } else {
            throw new UnsupportedOperationException("Unsupported message type.");
        }
    }
}
