package com.pps.asciigame.common.model;

import java.io.Serializable;

public enum OperationType implements Serializable {
	STEAL_1(5),
	BURN_1(5),
	STEAL_2(10),
	BURN_2(10);
	
	private final int range;
	
	OperationType(final int range){
		this.range = range;
	}

	public int getRange() {
		return range;
	}
}
