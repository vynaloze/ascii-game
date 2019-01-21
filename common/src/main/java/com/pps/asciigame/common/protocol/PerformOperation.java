package com.pps.asciigame.common.protocol;

import com.pps.asciigame.common.model.Operation;
import com.pps.asciigame.common.model.User;

public class PerformOperation extends Message {
	private final Operation operation;

	public PerformOperation(User user, Operation operation) {
		super(user);
		this.operation = operation;
	}
	
	public Operation getOperation() {
        return operation;
    }

}
