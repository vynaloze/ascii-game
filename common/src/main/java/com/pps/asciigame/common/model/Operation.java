package com.pps.asciigame.common.model;

import java.io.Serializable;

public class Operation implements Serializable {
    private String costs;
    private String effects;
    private Building requiredBuilding;
    private Base homeBase;
    private Base targetBase;
    private OperationType operationType;

    public Operation(String costs, String effects, String requirements, Building requiredBuilding, Base homeBase, Base targetBase, OperationType operationType) {
        this.costs = costs;
        this.effects = effects;
        this.requiredBuilding = requiredBuilding;
        this.homeBase = homeBase;
        this.targetBase = targetBase;
        this.operationType = operationType;
    }

    public String getCosts() {
        return costs;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public Base getHomeBase() {
        return homeBase;
    }

    public void setHomeBase(Base homeBase) {
        this.homeBase = homeBase;
    }

    public Building getRequiredBuilding() {
        return requiredBuilding;
    }

    public void setRequiredBuilding(Building requiredBuilding) {
        this.requiredBuilding = requiredBuilding;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Base getTargetBase() {
        return targetBase;
    }

    public void setTargetBase(Base targetBase) {
        this.targetBase = targetBase;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Operation other = (Operation) obj;
        if (costs == null) {
            if (other.costs != null) {
                return false;
            }
        } else if (!costs.equals(other.costs)) {
            return false;
        }
        if (effects == null) {
            if (other.effects != null) {
                return false;
            }
        } else if (!effects.equals(other.effects)) {
            return false;
        }
        if (homeBase == null) {
            if (other.homeBase != null) {
                return false;
            }
        } else if (!homeBase.equals(other.homeBase)) {
            return false;
        }
        if (operationType != other.operationType) {
            return false;
        }
        if (requiredBuilding == null) {
            if (other.requiredBuilding != null) {
                return false;
            }
        } else if (!requiredBuilding.equals(other.requiredBuilding)) {
            return false;
        }
        if (targetBase == null) {
            if (other.targetBase != null) {
                return false;
            }
        } else if (!targetBase.equals(other.targetBase)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((costs == null) ? 0 : costs.hashCode());
        result = prime * result + ((effects == null) ? 0 : effects.hashCode());
        result = prime * result + ((homeBase == null) ? 0 : homeBase.hashCode());
        result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
        result = prime * result + ((requiredBuilding == null) ? 0 : requiredBuilding.hashCode());
        result = prime * result + ((targetBase == null) ? 0 : targetBase.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Operation [costs=" + costs + ", effects=" + effects + ", requiredBuilding="
                + requiredBuilding + ", homeBase=" + homeBase + ", targetBase=" + targetBase + ", operationType="
                + operationType + "]";
    }
}
