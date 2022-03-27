package com.github.mapper.models.many.to.many.case2;

import java.util.List;
import java.util.Objects;

public class Round2Lvl1 {

    private Long id;

    private String category;

    private List<Round1Lvl1> round1Lvl1s;

    public Round2Lvl1() {
    }

    public Round2Lvl1(Long id, String category, List<Round1Lvl1> round1Lvl1s) {
        this.id = id;
        this.category = category;
        this.round1Lvl1s = round1Lvl1s;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public List<Round1Lvl1> getRound1Lvl1s() {
        return round1Lvl1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round2Lvl1)) return false;
        Round2Lvl1 that = (Round2Lvl1) o;
        return Objects.equals(id, that.id) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }

    @Override
    public String toString() {
        return "Round2Lvl1{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
