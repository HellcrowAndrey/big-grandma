package com.github.mapper.case2.alpha;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Branch2Lvl1Case2 {

    private Long id;

    private String branchName;

    private RootCase2 rootCase2;

    private List<ABranch2Lvl2Case2> aBranch2Lvl2Case2s = new ArrayList<>();

    private List<BBranch2Lvl2Case2> bBranch2Lvl2Case2s = new ArrayList<>();

    public Branch2Lvl1Case2() {
    }

    public Branch2Lvl1Case2(Long id, String branchName) {
        this.id = id;
        this.branchName = branchName;
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

    public List<ABranch2Lvl2Case2> getaBranch2Lvl2Case2s() {
        return aBranch2Lvl2Case2s;
    }

    public void setaBranch2Lvl2Case2s(List<ABranch2Lvl2Case2> aBranch2Lvl2Case2s) {
        this.aBranch2Lvl2Case2s = aBranch2Lvl2Case2s;
    }

    public List<BBranch2Lvl2Case2> getbBranch2Lvl2Case2s() {
        return bBranch2Lvl2Case2s;
    }

    public void setbBranch2Lvl2Case2s(List<BBranch2Lvl2Case2> bBranch2Lvl2Case2s) {
        this.bBranch2Lvl2Case2s = bBranch2Lvl2Case2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch2Lvl1Case2)) return false;
        Branch2Lvl1Case2 that = (Branch2Lvl1Case2) o;
        return Objects.equals(id, that.id) && Objects.equals(branchName, that.branchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branchName);
    }

    @Override
    public String toString() {
        return "Branch2Lvl1Case2{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                '}';
    }

}
