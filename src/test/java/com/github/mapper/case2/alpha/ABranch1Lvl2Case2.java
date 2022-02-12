package com.github.mapper.case2.alpha;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ABranch1Lvl2Case2 {

    private Long id;

    private String bbranchName;

    private Branch1Lvl1Case2 branch1Lvl1Case2;

    private Set<ABranch1Lvl3Case2> aBranch1Lvl3Case2s = new HashSet<>();

    private Set<BBranch1Lvl3Case2> bBranch1Lvl3Case2s = new HashSet<>();

    public ABranch1Lvl2Case2() {
    }

    public ABranch1Lvl2Case2(Long id, String bbranchName) {
        this.id = id;
        this.bbranchName = bbranchName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBbranchName() {
        return bbranchName;
    }

    public void setBbranchName(String bbranchName) {
        this.bbranchName = bbranchName;
    }

    public Branch1Lvl1Case2 getBranch1Lvl1Case2() {
        return branch1Lvl1Case2;
    }

    public void setBranch1Lvl1Case2(Branch1Lvl1Case2 branch1Lvl1Case2) {
        this.branch1Lvl1Case2 = branch1Lvl1Case2;
    }

    public Set<ABranch1Lvl3Case2> getaBranch1Lvl3Case2s() {
        return aBranch1Lvl3Case2s;
    }

    public void setaBranch1Lvl3Case2s(Set<ABranch1Lvl3Case2> aBranch1Lvl3Case2s) {
        this.aBranch1Lvl3Case2s = aBranch1Lvl3Case2s;
    }

    public Set<BBranch1Lvl3Case2> getbBranch1Lvl3Case2s() {
        return bBranch1Lvl3Case2s;
    }

    public void setbBranch1Lvl3Case2s(Set<BBranch1Lvl3Case2> bBranch1Lvl3Case2s) {
        this.bBranch1Lvl3Case2s = bBranch1Lvl3Case2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABranch1Lvl2Case2)) return false;
        ABranch1Lvl2Case2 that = (ABranch1Lvl2Case2) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(bbranchName, that.bbranchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bbranchName);
    }

    @Override
    public String toString() {
        return "ABranch1Lvl2Case2{" +
                "id=" + id +
                ", bbranchName='" + bbranchName + '\'' +
                '}';
    }
}
