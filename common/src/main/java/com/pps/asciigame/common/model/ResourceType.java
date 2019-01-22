package com.pps.asciigame.common.model;

import java.io.Serializable;

public enum ResourceType implements Serializable {
    GOLD(2000), MINERAL(2000), FOOD(1000);

    private final double intialAmount;

    ResourceType(final double intialAmount) {
        this.intialAmount = intialAmount;
    }

    public double getIntialAmount() {
        return intialAmount;
    }

}
