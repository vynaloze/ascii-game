package com.pps.asciigame.client.game;

import com.pps.asciigame.common.protocol.BasicInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DataReceivingController {
    private static final Logger LOGGER = LogManager.getLogger(DataReceivingController.class);

    public void updateBasicInfo(final BasicInfo basicInfo) {
        LOGGER.info("Sever sent " + basicInfo);
    }
}
