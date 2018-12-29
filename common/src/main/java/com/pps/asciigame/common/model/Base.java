package com.pps.asciigame.common.model;

import javax.persistence.*;

@Entity
@Table(name = "bases")
public class Base {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private User owner;

    public Base(int x, int y, String name, User owner) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.owner = owner;
    }

    protected Base() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(final User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Base)) {
            return false;
        }

        Base base = (Base) o;

        if (id != base.id) {
            return false;
        }
        if (x != base.x) {
            return false;
        }
        if (y != base.y) {
            return false;
        }
        if (name != null ? !name.equals(base.name) : base.name != null) {
            return false;
        }
        return owner != null ? owner.equals(base.owner) : base.owner == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
