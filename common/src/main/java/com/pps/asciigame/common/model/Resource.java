package com.pps.asciigame.common.model;

import javax.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ResourceType type;

    @Column(name = "growth_rate")
    private Double growthRate;

    public Resource(User user, Double amount, ResourceType type, Double growthRate) {
        this.user = user;
        this.amount = amount;
        this.type = type;
        this.growthRate = growthRate;
    }

    protected Resource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null) {
            return false;
        }
        if (user != null ? !user.equals(resource.user) : resource.user != null) {
            return false;
        }
        if (amount != null ? !amount.equals(resource.amount) : resource.amount != null) {
            return false;
        }
        if (type != resource.type) {
            return false;
        }
        return growthRate != null ? growthRate.equals(resource.growthRate) : resource.growthRate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (growthRate != null ? growthRate.hashCode() : 0);
        return result;
    }
}
