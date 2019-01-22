package com.pps.asciigame.ws.web;

import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.User;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.protocol.BuildBase;
import com.pps.asciigame.common.protocol.BuildBuilding;
import com.pps.asciigame.common.protocol.ChatEntry;
import com.pps.asciigame.common.protocol.PerformOperation;

import java.time.LocalDateTime;

public abstract class TestBase {
    protected final User testUser = new User("testUser");
    protected final Building testBuilding = new Building();
    protected final Base testBase = new Base();
    protected final Base testBase2 = new Base();
    protected final Operation testOperation = new Operation(testBase, testBase2, null);
    protected final ChatEntry chatEntry = new ChatEntry(LocalDateTime.now(), testUser, "Message");
    protected final PerformOperation performOperation = new PerformOperation(testUser, testOperation);
    protected final BuildBuilding buildBuilding = new BuildBuilding(testUser, testBuilding);
    protected final BuildBase buildBase = new BuildBase(testUser, testBase);
}
