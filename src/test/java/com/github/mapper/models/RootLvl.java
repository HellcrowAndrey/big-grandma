package com.github.mapper.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RootLvl {

    private Long id;

    private String vinCode;

    private String name;

    private String firstName;

    private String lastName;

    private Round1Lvl1 round1Lvl1;

    private List<Round2Lvl1> round2Lvl1s = new ArrayList<>();

    private List<Round3ManyToManyLvl1> round3ManyToManyLvl1s = new ArrayList<>();

    public RootLvl() {
    }

    public RootLvl(Long id, String vinCode, String name, String firstName, String lastName) {
        this.id = id;
        this.vinCode = vinCode;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public RootLvl(Long id, String vinCode, String name, String firstName, String lastName, Round1Lvl1 round1Lvl1) {
        this.id = id;
        this.vinCode = vinCode;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.round1Lvl1 = round1Lvl1;
    }

    public Long getId() {
        return id;
    }

    public String getVinCode() {
        return vinCode;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Round1Lvl1 getRound1Lvl1() {
        return round1Lvl1;
    }

    public List<Round2Lvl1> getRound2Lvl1s() {
        return round2Lvl1s;
    }

    public List<Round3ManyToManyLvl1> getRound3ManyToManyLvl1s() {
        return round3ManyToManyLvl1s;
    }

    public RootLvl round1Lvl1(Round1Lvl1 round1Lvl1) {
        this.round1Lvl1 = round1Lvl1;
        this.round1Lvl1.setRootLvl(this);
        return this;
    }

    public RootLvl round2Lvl1s(Round2Lvl1 round2Lvl1) {
        round2Lvl1.setRootLvl(this);
        this.round2Lvl1s.add(round2Lvl1);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootLvl)) return false;
        RootLvl rootLvl = (RootLvl) o;
        return Objects.equals(id, rootLvl.id)
                && Objects.equals(vinCode, rootLvl.vinCode)
                && Objects.equals(name, rootLvl.name)
                && Objects.equals(firstName, rootLvl.firstName)
                && Objects.equals(lastName, rootLvl.lastName)
                && Objects.equals(round1Lvl1, rootLvl.round1Lvl1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                vinCode,
                name,
                firstName,
                lastName,
                round1Lvl1,
                round2Lvl1s
        );
    }

    public boolean isEquals(Round1Lvl1 input) {
        return input.equals(this.round1Lvl1);
    }

    @Override
    public String toString() {
        return "RootLvl{" +
                "id=" + id +
                ", vinCode='" + vinCode + '\'' +
                ", name='" + name + '\'' +
                ", fieldName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
