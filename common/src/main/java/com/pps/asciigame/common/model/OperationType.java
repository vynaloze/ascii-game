package com.pps.asciigame.common.model;

import static com.pps.asciigame.common.model.ResourceType.FOOD;
import static com.pps.asciigame.common.model.ResourceType.GOLD;
import static com.pps.asciigame.common.model.ResourceType.MINERAL;

import java.io.Serializable;

public enum OperationType implements Serializable {
	STEAL(5, "steal", 0.1, 
			new ResourceAmounts.Builder()
            .withType(GOLD, 500.0)
            .withType(MINERAL, 0.0)
            .withType(FOOD, 0.0)
            .build()),
	BURN(5, "burn", 0.1,
			new ResourceAmounts.Builder()
            .withType(GOLD, 500.0)
            .withType(MINERAL, 0.0)
            .withType(FOOD, 0.0)
            .build());
	
	private final int range;
	private final String effect;
	private final double percent;
	private final ResourceAmounts costs;
	
	OperationType(final int range, final String effect, final double percent, final ResourceAmounts costs){
		this.range = range;
		this.effect = effect;
		this.percent = percent;
		this.costs = costs;
	}

	public int getRange() {
		return range;
	}

	public String getEffect() {
		return effect;
	}

	public double getPercent() {
		return percent;
	}

	public ResourceAmounts getCosts() {
		return costs;
	}
	
}
