package com.pps.asciigame.client.game;

import com.pps.asciigame.client.ui.MainFXMLController;
import com.pps.asciigame.client.ui.map.MapUI;
import com.pps.asciigame.client.ui.utils.ParameterForwarder;
import com.pps.asciigame.client.ui.utils.ScenesManager;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.protocol.BasicInfo;
import com.pps.asciigame.common.protocol.Confirmation;
import com.pps.asciigame.common.protocol.MapData;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DataReceivingController {
    private static final Logger LOGGER = LogManager.getLogger(DataReceivingController.class);
    @Autowired
    private MainFXMLController mainFXMLController;
    @Autowired
    private MapUI mapUI;
    @Autowired
    private ParameterForwarder parameterForwarder;

    public void updateBasicInfo(final BasicInfo basicInfo) {
        LOGGER.info("Sever sent " + basicInfo);
        mainFXMLController.updateBasicInfo(basicInfo);
    }

    public void updateMapData(final MapData mapData) {
        LOGGER.info("Server send mapData: " + mapData.getBases().stream().map(Base::toString).collect(Collectors.joining(", ")));
        mapUI.setBases(mapData.getBases());
    }

    public void showConfirmation(final Confirmation confirmation) {
        parameterForwarder.pass(confirmation, Confirmation.class);
        Platform.runLater(() -> ScenesManager.loadScene(ScenesManager.Scenes.CONFIRMATION));
    }

}
