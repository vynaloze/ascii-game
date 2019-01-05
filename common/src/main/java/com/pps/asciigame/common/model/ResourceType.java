package com.pps.asciigame.common.model;

import java.io.Serializable;

public enum ResourceType implements Serializable {
    GOLD(1000), MINERAL(2000), FOOD(500);

    private final double intialAmount;

    ResourceType(final double intialAmount) {
        this.intialAmount = intialAmount;
    }

    public double getIntialAmount() {
        return intialAmount;
    }

}
