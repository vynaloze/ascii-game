package com.pps.asciigame.ws.web;

import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.BuildingType;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.util.BuildingFactory;

import java.time.LocalDateTime;

public abstract class AbstractTestData {
    protected final User testUser = new User("testUser");
    protected final ChatEntry chatEntry = new ChatEntry(LocalDateTime.now(), testUser, "Message");
    protected final Base testBase = new Base(6, 9, "testBase", testUser);
    protected final Building testBuildingA = BuildingFactory.createBuilding(testBase, BuildingType.A);

    //todo resources here
}
