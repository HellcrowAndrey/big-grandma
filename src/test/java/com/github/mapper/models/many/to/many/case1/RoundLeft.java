package com.github.mapper.models.many.to.many.case1;

import java.util.List;
import java.util.Objects;

public class RoundLeft {

    private Long id;

    private String type;

    private List<RootManyToMany> roundRight;

    public RoundLeft() {
    }

    public RoundLeft(Long id, String type, List<RootManyToMany> roundRight) {
        this.id = id;
        this.type = type;
        this.roundRight = roundRight;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<RootManyToMany> getRoundRight() {
        return roundRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoundLeft)) return false;
        RoundLeft roundLeft = (RoundLeft) o;
        return Objects.equals(id, roundLeft.id) && Objects.equals(type, roundLeft.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "RoundLeft{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
