package com.pps.asciigame.client.game;

import com.pps.asciigame.client.ui.MainFXMLController;
import com.pps.asciigame.common.protocol.BasicInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataReceivingController {
    private static final Logger LOGGER = LogManager.getLogger(DataReceivingController.class);
    @Autowired
    private MainFXMLController mainFXMLController;

    public void updateBasicInfo(final BasicInfo basicInfo) {
        LOGGER.info("Sever sent " + basicInfo);
        mainFXMLController.updateBasicInfo(basicInfo);
    }
}
