package com.pps.asciigame.client;

import com.pps.asciigame.client.chat.ChatController;
import com.pps.asciigame.client.game.DataReceivingController;
import com.pps.asciigame.common.Dispatcher;
import com.pps.asciigame.common.protocol.BasicInfo;
import com.pps.asciigame.common.protocol.BuildBuilding;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.Message;
import com.pps.asciigame.common.protocol.PerformOperation;

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
//        } else if (message instanceof BuildBuilding) {
//            // todo - what shoud go here?
//        } else if (message instanceof PerformOperation) {
//            // todo - what should go here
        } else {
            throw new UnsupportedOperationException("Unsupported message type.");
        }
    }
}
