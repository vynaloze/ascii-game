package com.pps.asciigame.common.util;

import com.pps.asciigame.common.JsonParser;
import com.pps.asciigame.common.model.Base;
import com.pps.asciigame.common.model.Building;
import com.pps.asciigame.common.model.BuildingType;

public class BuildingFactory {
    public static Building createBuilding(final Base base, final BuildingType type) {
        final var costs = JsonParser.stringify(type.getCost());
        final var profits = JsonParser.stringify(type.getProfit());
        return new Building(base, type, costs, profits);
    }
}
