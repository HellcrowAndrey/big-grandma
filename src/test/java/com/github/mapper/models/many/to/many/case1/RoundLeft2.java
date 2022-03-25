package com.github.mapper.models.many.to.many.case1;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RoundLeft2 {

    private Long id;

    private boolean isType;

    private Set<RootManyToMany> roundRights;

    public RoundLeft2() {
    }

    public RoundLeft2(Long id, boolean isType, Set<RootManyToMany> roundRights) {
        this.id = id;
        this.isType = isType;
        this.roundRights = roundRights;
    }

    public Long getId() {
        return id;
    }

    public boolean isType() {
        return isType;
    }

    public Set<RootManyToMany> getRoundRights() {
        return roundRights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundLeft2)) return false;
        RoundLeft2 that = (RoundLeft2) o;
        return isType == that.isType && Objects.equals(id, that.id) && Objects.equals(roundRights, that.roundRights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isType, roundRights);
    }

    @Override
    public String toString() {
        return "RoundLeft2{" +
                "id=" + id +
                ", isType=" + isType +
                '}';
    }
}
