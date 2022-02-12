package com.github.mapper.case2.alpha;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Branch1Lvl1Case2 {

    private Long id;

    private String branchName;

    private RootCase2 rootCase2;

    private List<ABranch1Lvl2Case2> aBranch1Lvl2Case2s = new ArrayList<>();

    private List<BBranch1Lvl2Case2> branch1Lvl2Case2s = new ArrayList<>();

    public Branch1Lvl1Case2() {
    }

    public Branch1Lvl1Case2(Long id, String branchName, RootCase2 rootCase2) {
        this.id = id;
        this.branchName = branchName;
        this.rootCase2 = rootCase2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public RootCase2 getRootCase2() {
        return rootCase2;
    }

    public void setRootCase2(RootCase2 rootCase2) {
        this.rootCase2 = rootCase2;
    }

    public List<ABranch1Lvl2Case2> getaBranch1Lvl2Case2s() {
        return aBranch1Lvl2Case2s;
    }

    public void setaBranch1Lvl2Case2s(List<ABranch1Lvl2Case2> aBranch1Lvl2Case2s) {
        this.aBranch1Lvl2Case2s = aBranch1Lvl2Case2s;
    }

    public List<BBranch1Lvl2Case2> getBranch1Lvl2Case2s() {
        return branch1Lvl2Case2s;
    }

    public void setBranch1Lvl2Case2s(List<BBranch1Lvl2Case2> branch1Lvl2Case2s) {
        this.branch1Lvl2Case2s = branch1Lvl2Case2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch1Lvl1Case2)) return false;
        Branch1Lvl1Case2 that = (Branch1Lvl1Case2) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(branchName, that.branchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branchName);
    }

    @Override
    public String toString() {
        return "Branch1Lvl1Case2{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
