package com.pps.asciigame.common.model;

import java.util.Map;

import static com.pps.asciigame.common.model.ResourceType.*;

public enum BuildingType {
    CENTRAL(Map.of(GOLD, 0.0, MINERAL, 0.0, FOOD, 0.0),
            Map.of(GOLD, 10.0, MINERAL, 10.0, FOOD, 5.0)),
    A(Map.of(GOLD, 100.0, MINERAL, 100.0, FOOD, 20.0),
            Map.of(GOLD, 5.0, MINERAL, 2.0, FOOD, -1.0));

    private final Map<ResourceType, Double> cost;
    private final Map<ResourceType, Double> profit;

    BuildingType(final Map<ResourceType, Double> cost, final Map<ResourceType, Double> profit) {
        this.cost = cost;
        this.profit = profit;
    }

    public Map<ResourceType, Double> getCost() {
        return cost;
    }

    public Map<ResourceType, Double> getProfit() {
        return profit;
    }
}
