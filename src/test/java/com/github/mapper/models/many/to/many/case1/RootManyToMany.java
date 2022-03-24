package com.github.mapper.models.many.to.many.case1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

public class RootManyToMany {

    private long id;

    private String firstName;

    private String lastName;

    private List<RoundLeft> roundLefts;

    private List<Round1Lvl1MTM> round1Lvl1MTMS;

    public RootManyToMany() {
    }

    public RootManyToMany(Long id, String firstName, String lastName, List<RoundLeft> roundLefts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roundLefts = roundLefts;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<RoundLeft> getRoundLefts() {
        return roundLefts;
    }

    public List<Round1Lvl1MTM> getRound1Lvl1MTMS() {
        return round1Lvl1MTMS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootManyToMany)) return false;
        RootManyToMany that = (RootManyToMany) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(roundLefts, that.roundLefts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, roundLefts);
    }

    @Override
    public String toString() {
        return "RootManyToMany{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roundLefts=" + roundLefts +
                '}';
    }
}
