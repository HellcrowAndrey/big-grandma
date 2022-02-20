package com.github.mapper.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Round3ManyToManyLvl1 {

    private Long id;

    private List<Round4ManyToManyLvl2> round4ManyToManyLvl2s = new ArrayList<>();

    public Round3ManyToManyLvl1() {
    }

    public Round3ManyToManyLvl1(Long id, List<Round4ManyToManyLvl2> round4ManyToManyLvl2s) {
        this.id = id;
        this.round4ManyToManyLvl2s = round4ManyToManyLvl2s;
    }

    public Long getId() {
        return id;
    }

    public List<Round4ManyToManyLvl2> getRound4ManyToManyLvl1s() {
        return round4ManyToManyLvl2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round3ManyToManyLvl1)) return false;
        Round3ManyToManyLvl1 that = (Round3ManyToManyLvl1) o;
        return Objects.equals(id, that.id) && Objects.equals(round4ManyToManyLvl2s, that.round4ManyToManyLvl2s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round4ManyToManyLvl2s);
    }

    @Override
    public String toString() {
        return "Round3ManyToManyLvl1{" +
                "id=" + id +
                ", round4ManyToManyLvl1s=" + round4ManyToManyLvl2s +
                '}';
    }
}
