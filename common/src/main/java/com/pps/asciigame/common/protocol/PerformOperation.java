package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.model.User;

public class PerformOperation extends Message {
	private final Operation operation;
	private final User targetBase;

	protected PerformOperation(User user, Operation operation, User targetBase) {
		super(user);
		this.operation = operation;
		this.targetBase = targetBase;
	}
	
	public Operation getOperation() {
        return operation;
    }

	public User getTargetBase() {
		return targetBase;
	}

}
