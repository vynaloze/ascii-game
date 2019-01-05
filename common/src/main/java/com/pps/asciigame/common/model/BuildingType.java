package com.pps.asciigame.common.model;

import java.io.Serializable;

import static com.pps.asciigame.common.model.ResourceType.*;

public enum BuildingType implements Serializable {
    CENTRAL(
            new ResourceAmounts.Builder()
                    .withType(GOLD, 1000.0)
                    .withType(MINERAL, 1000.0)
                    .withType(FOOD, 500.0)
                    .build(),
            new ResourceAmounts.Builder()
                    .withType(GOLD, 10.0)
                    .withType(MINERAL, 10.0)
                    .withType(FOOD, 5.0)
                    .build()
    ),
    A(
            new ResourceAmounts.Builder()
                    .withType(GOLD, 1000.0)
                    .withType(MINERAL, 1000.0)
                    .withType(FOOD, 500.0)
                    .build(),
            new ResourceAmounts.Builder()
                    .withType(GOLD, 5.0)
                    .withType(MINERAL, 2.0)
                    .withType(FOOD, -1.0)
                    .build()
    );

    private final ResourceAmounts cost;
    private final ResourceAmounts profit;

    BuildingType(final ResourceAmounts cost, final ResourceAmounts profit) {
        this.cost = cost;
        this.profit = profit;
    }

    public ResourceAmounts getCost() {
        return cost;
    }

    public ResourceAmounts getProfit() {
        return profit;
    }
}
