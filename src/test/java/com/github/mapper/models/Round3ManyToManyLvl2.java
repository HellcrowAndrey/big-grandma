package com.github.mapper.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Round3ManyToManyLvl2 {

    private Long id;

    private List<Round4ManyToManyLvl3> round4ManyToManyLvl3s = new ArrayList<>();

    public Round3ManyToManyLvl2() {
    }

    public Round3ManyToManyLvl2(Long id, List<Round4ManyToManyLvl3> round4ManyToManyLvl3s) {
        this.id = id;
        this.round4ManyToManyLvl3s = round4ManyToManyLvl3s;
    }

    public Long getId() {
        return id;
    }

    public List<Round4ManyToManyLvl3> getRound4ManyToManyLvl1s() {
        return round4ManyToManyLvl3s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round3ManyToManyLvl2)) return false;
        Round3ManyToManyLvl2 that = (Round3ManyToManyLvl2) o;
        return Objects.equals(id, that.id) && Objects.equals(round4ManyToManyLvl3s, that.round4ManyToManyLvl3s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round4ManyToManyLvl3s);
    }

    @Override
    public String toString() {
        return "Round3ManyToManyLvl1{" +
                "id=" + id +
                ", round4ManyToManyLvl1s=" + round4ManyToManyLvl3s +
                '}';
    }
}
