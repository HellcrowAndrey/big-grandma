package com.github.mapper.case1;

import java.util.Objects;

public class Branch1Lvl1Case1 {

    private Long id;

    private String branchName;

    private Integer graphLvlOne;

    public Branch1Lvl1Case1() {
    }

    public Branch1Lvl1Case1(Long id, String branchName, Integer graphLvlOne) {
        this.id = id;
        this.branchName = branchName;
        this.graphLvlOne = graphLvlOne;
    }

    public Long getId() {
        return id;
    }

    public String getBranchName() {
        return branchName;
    }

    public Integer getGraphLvlOne() {
        return graphLvlOne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch1Lvl1Case1)) return false;
        Branch1Lvl1Case1 that = (Branch1Lvl1Case1) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(branchName, that.branchName) &&
                Objects.equals(graphLvlOne, that.graphLvlOne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branchName, graphLvlOne);
    }

    @Override
    public String toString() {
        return "Branch1Lvl1Case1{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", graphLvlOne=" + graphLvlOne +
                '}';
    }
}
