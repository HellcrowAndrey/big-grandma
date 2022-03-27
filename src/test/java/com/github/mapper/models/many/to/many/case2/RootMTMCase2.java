package com.github.mapper.models.many.to.many.case2;

import java.util.List;
import java.util.Objects;

public class RootMTMCase2 {

    private Long id;

    private String name;

    private List<Round1Lvl1> round1Lvl1s;

    public RootMTMCase2() {
    }

    public RootMTMCase2(Long id, String name, List<Round1Lvl1> round1Lvl1s) {
        this.id = id;
        this.name = name;
        this.round1Lvl1s = round1Lvl1s;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Round1Lvl1> getRound1Lvl1s() {
        return round1Lvl1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootMTMCase2)) return false;
        RootMTMCase2 that = (RootMTMCase2) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(round1Lvl1s, that.round1Lvl1s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, round1Lvl1s);
    }

    @Override
    public String toString() {
        return "RootMTMCase2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
