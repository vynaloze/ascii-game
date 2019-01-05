package com.pps.asciigame.common.model;

public enum ResourceType {
    GOLD(1000), MINERAL(2000), FOOD(500);

    private final double intialAmount;

    ResourceType(final double intialAmount) {
        this.intialAmount = intialAmount;
    }

    public double getIntialAmount() {
        return intialAmount;
    }

}
