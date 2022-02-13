package com.github.mapper.case1;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SubGraphLvl2Case1 {

    private Long id;

    private String name;

    private String plusOne;

    //manyToOne
    private RootCase1 rootCase1;

    private Set<SubGraphLvl3Case1> subGraphLvl3Case1s = new HashSet<>();

    public SubGraphLvl2Case1() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlusOne() {
        return plusOne;
    }

    public void setPlusOne(String plusOne) {
        this.plusOne = plusOne;
    }

    public RootCase1 getRootCase1() {
        return rootCase1;
    }

    public void setRootCase1(RootCase1 rootCase1) {
        this.rootCase1 = rootCase1;
    }

    public Set<SubGraphLvl3Case1> getSubGraphLvl3Case1s() {
        return subGraphLvl3Case1s;
    }

    public void setSubGraphLvl3Case1s(Set<SubGraphLvl3Case1> subGraphLvl3Case1s) {
        this.subGraphLvl3Case1s = subGraphLvl3Case1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubGraphLvl2Case1)) return false;
        SubGraphLvl2Case1 that = (SubGraphLvl2Case1) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(plusOne, that.plusOne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, plusOne);
    }

    @Override
    public String toString() {
        return "SubGraphLvl2Case1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plusOne='" + plusOne + '\'' +
                '}';
    }
}
