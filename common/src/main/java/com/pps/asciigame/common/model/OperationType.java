package com.pps.asciigame.common.model;

import java.io.Serializable;

public enum OperationType implements Serializable {
	STEAL_1(5, "steal", 0.1),
	BURN_1(5, "burn", 0.1),
	STEAL_2(10, "steal", 0.2),
	BURN_2(10, "burn", 0.2);
	
	private final int range;
	private final String effect;
	private final double percent;
	
	OperationType(final int range, final String effect, final double percent){
		this.range = range;
		this.effect = effect;
		this.percent = percent;
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
	
}
