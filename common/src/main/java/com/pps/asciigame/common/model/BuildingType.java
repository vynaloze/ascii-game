package com.pps.asciigame.common.model;

import java.io.Serializable;

import static com.pps.asciigame.common.model.ResourceType.*;

public enum BuildingType implements Serializable {
    CITY_HALL(
            new ResourceAmounts.Builder()
                    .withType(GOLD, 1000.0)
                    .withType(MINERAL, 1000.0)
                    .withType(FOOD, 500.0)
                    .build(),
            new ResourceAmounts.Builder()
                    .withType(GOLD, 50.0)
                    .withType(MINERAL, 50.0)
                    .withType(FOOD, 50.0)
                    .build()
    ),
    WATCHTOWER(
            new ResourceAmounts.Builder()
                    .withType(GOLD, 100.0)
                    .withType(MINERAL, 100.0)
                    .withType(FOOD, 0.0)
                    .build(),
            new ResourceAmounts.Builder()
                    .withType(GOLD, 0.0)
                    .withType(MINERAL, 0.0)
                    .withType(FOOD, 0.0)
                    .build()
    ),
    MINE(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 250.0)
            .withType(MINERAL, 250.0)
            .withType(FOOD, 0.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 0.0)
            .withType(MINERAL, 100.0)
            .withType(FOOD, 0.0)
            .build()
	),
    SUPER_MINE(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 500.0)
            .withType(MINERAL, 500.0)
            .withType(FOOD, 0.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 0.0)
            .withType(MINERAL, 300.0)
            .withType(FOOD, 0.0)
            .build()
	),
    FARM(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 0.0)
            .withType(MINERAL, 250.0)
            .withType(FOOD, 250.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 0.0)
            .withType(MINERAL, 0.0)
            .withType(FOOD, 100.0)
            .build()
	),
    SUPER_FARM(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 0.0)
            .withType(MINERAL, 500.0)
            .withType(FOOD, 500.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 0.0)
            .withType(MINERAL, 0.0)
            .withType(FOOD, 300.0)
            .build()
    ),
    GOLDMINE(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 500.0)
            .withType(MINERAL, 500.0)
            .withType(FOOD, 0.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 100.0)
            .withType(MINERAL, 0.0)
            .withType(FOOD, 0.0)
            .build()
    ),
    SUPER_GOLDMINE(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 1000.0)
            .withType(MINERAL, 1000.0)
            .withType(FOOD, 0.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 300.0)
            .withType(MINERAL, 0.0)
            .withType(FOOD, 0.0)
            .build()
    ),
    PALACE(
    		new ResourceAmounts.Builder()
            .withType(GOLD, 4000.0)
            .withType(MINERAL, 4000.0)
            .withType(FOOD, 4000.0)
            .build(),
    new ResourceAmounts.Builder()
            .withType(GOLD, 300.0)
            .withType(MINERAL, 300.0)
            .withType(FOOD, 300.0)
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
