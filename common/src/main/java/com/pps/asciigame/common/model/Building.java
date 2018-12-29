package com.pps.asciigame.common.model;

import javax.persistence.*;

@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bases", referencedColumnName = "id")
    private Base base;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BuildingType type;

    @Column(name = "costs")
    private String costs;

    @Column(name = "profits")
    private String profits;

    public Building(Base base, BuildingType type, String costs, String profits) {
        this.base = base;
        this.type = type;
        this.costs = costs;
        this.profits = profits;
    }

    public Building() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public String getCosts() {
        return costs;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getProfits() {
        return profits;
    }

    public void setProfits(String profits) {
        this.profits = profits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Building)) {
            return false;
        }

        Building building = (Building) o;

        if (id != null ? !id.equals(building.id) : building.id != null) {
            return false;
        }
        if (base != null ? !base.equals(building.base) : building.base != null) {
            return false;
        }
        if (type != building.type) {
            return false;
        }
        if (costs != null ? !costs.equals(building.costs) : building.costs != null) {
            return false;
        }
        return profits != null ? profits.equals(building.profits) : building.profits == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (costs != null ? costs.hashCode() : 0);
        result = 31 * result + (profits != null ? profits.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", base=" + base +
                ", type=" + type +
                ", costs='" + costs + '\'' +
                ", profits='" + profits + '\'' +
                '}';
    }
}
