package com.pps.asciigame.common.model;

public enum ResourceType {
    GOLD(100), MINERAL(200), FOOD(50);

    private final double intialAmount;

    ResourceType(final double intialAmount) {
        this.intialAmount = intialAmount;
    }

    public double getIntialAmount() {
        return intialAmount;
    }

}
