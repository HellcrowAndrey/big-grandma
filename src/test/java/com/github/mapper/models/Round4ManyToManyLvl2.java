package com.github.mapper.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Round4ManyToManyLvl2 {

    private Long id;

    private List<Round3ManyToManyLvl1> round3ManyToManyLvl1s = new ArrayList<>();

    public Round4ManyToManyLvl2() {
    }

    public Round4ManyToManyLvl2(Long id, List<Round3ManyToManyLvl1> round3ManyToManyLvl1s) {
        this.id = id;
        this.round3ManyToManyLvl1s = round3ManyToManyLvl1s;
    }

    public Long getId() {
        return id;
    }

    public List<Round3ManyToManyLvl1> getRound3ManyToManyLvl1s() {
        return round3ManyToManyLvl1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round4ManyToManyLvl2)) return false;
        Round4ManyToManyLvl2 that = (Round4ManyToManyLvl2) o;
        return Objects.equals(id, that.id) && Objects.equals(round3ManyToManyLvl1s, that.round3ManyToManyLvl1s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round3ManyToManyLvl1s);
    }

    @Override
    public String toString() {
        return "Round4ManyToManyLvl1{" +
                "id=" + id +
                ", round3ManyToManyLvl1s=" + round3ManyToManyLvl1s +
                '}';
    }
}