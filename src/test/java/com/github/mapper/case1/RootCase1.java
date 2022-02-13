package com.github.mapper.case1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RootCase1 {

    private Long id;

    private String lastName;

    private String firstName;

    //oneToMany
    private List<SubGraphLvl2Case1> subGraphLvl2Case1 = new ArrayList<>();

    //oneToOne
    private Branch1Lvl1Case1 branch1Lvl1Case1;

    public RootCase1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<SubGraphLvl2Case1> getSubGraphCase1() {
        return subGraphLvl2Case1;
    }

    public void setSubGraphCase1(List<SubGraphLvl2Case1> subGraphLvl2Case1) {
        this.subGraphLvl2Case1 = subGraphLvl2Case1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootCase1)) return false;
        RootCase1 rootCase1 = (RootCase1) o;
        return Objects.equals(id, rootCase1.id) && Objects.equals(lastName, rootCase1.lastName) && Objects.equals(firstName, rootCase1.firstName) && Objects.equals(subGraphLvl2Case1, rootCase1.subGraphLvl2Case1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, subGraphLvl2Case1);
    }

    @Override
    public String toString() {
        return "RootCase1{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", subGraphCase1=" + subGraphLvl2Case1 +
                '}';
    }
}
