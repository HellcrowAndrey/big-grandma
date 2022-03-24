package com.github.mapper.models.many.to.many.case1;

import java.util.Objects;

public class Round1Lvl1MTM {

    private Long id;

    private String name;

    private Integer number;

    public Round1Lvl1MTM() {
    }

    public Round1Lvl1MTM(Long id, String name, Integer number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round1Lvl1MTM)) return false;
        Round1Lvl1MTM that = (Round1Lvl1MTM) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number);
    }

    @Override
    public String toString() {
        return "Round1Lvl1MTM{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
