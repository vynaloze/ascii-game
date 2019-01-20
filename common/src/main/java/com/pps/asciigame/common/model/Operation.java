package com.pps.asciigame.common.model;

import javax.persistence.*;
import java.io.Serializable;

public class Operation implements Serializable {

	@Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "costs")
    private String costs;

    @Column(name = "effects")
    private String effects;
    
    @Column(name = "requirements")
    private String requirements;
   
    public Operation(String costs, String effects, String requirements) {
    	this.costs = costs;
    	this.effects = effects;
    	this.requirements = requirements;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (costs == null) {
			if (other.costs != null)
				return false;
		} else if (!costs.equals(other.costs))
			return false;
		if (effects == null) {
			if (other.effects != null)
				return false;
		} else if (!effects.equals(other.effects))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
		result = prime * result + ((costs == null) ? 0 : costs.hashCode());
		result = prime * result + ((effects == null) ? 0 : effects.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());		
		return result;
	}

	@Override
	public String toString() {
		return "Operation [id=" + id + ", costs=" + costs + ", effects=" + effects + ", requirements =" + requirements + "]";
	}
}
