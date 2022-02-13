package com.github.mapper.models;

import java.util.Objects;

public class RootLvl {

    private Long id;

    private String vinCode;

    private String name;

    private String firstName;

    private String lastName;

    private Round1Lvl1 round1Lvl1;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootLvl)) return false;
        RootLvl rootLvl = (RootLvl) o;
        return Objects.equals(id, rootLvl.id) &&
                Objects.equals(vinCode, rootLvl.vinCode) &&
                Objects.equals(name, rootLvl.name) &&
                Objects.equals(firstName, rootLvl.firstName) &&
                Objects.equals(lastName, rootLvl.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vinCode, name, firstName, lastName);
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
