package com.pps.asciigame.common.model;

import java.io.Serializable;

public class Operation implements Serializable {
    private final Base homeBase;

    private final Base targetBase;

    private final OperationType operationType;

    public Operation(Base homeBase, Base targetBase, OperationType operationType) {
        this.homeBase = homeBase;
        this.targetBase = targetBase;
        this.operationType = operationType;
    }

    public Base getHomeBase() {
        return homeBase;
    }

    public Base getTargetBase() {
        return targetBase;
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
