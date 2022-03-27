package com.github.mapper.models.many.to.many.case2;

import java.util.List;
import java.util.Objects;

public class Round1Lvl1 {

    private Long id;

    private String type;

    private RootMTMCase2 rootMTMCase2;

    private List<Round2Lvl1> round2Lvl1s;

    public Round1Lvl1() {
    }

    public Round1Lvl1(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public RootMTMCase2 getRootMTMCase2() {
        return rootMTMCase2;
    }

    public List<Round2Lvl1> getRound2Lvl1s() {
        return round2Lvl1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round1Lvl1)) return false;
        Round1Lvl1 that = (Round1Lvl1) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "Round1Lvl1{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
