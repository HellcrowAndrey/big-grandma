package com.github.mapper.case2.alpha;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BBranch1Lvl2Case2 {

    private Long id;

    private String bbranchName;

    private Branch1Lvl1Case2 branch1Lvl1Case2;

    private Set<HBranch1Lvl3Case2> hBranch1Lvl3Case2s = new HashSet<>();

    private IBranch1Lvl3Case2 iBranch1Lvl3Case2;

    public BBranch1Lvl2Case2() {
    }

    public BBranch1Lvl2Case2(Long id, String bbranchName, Branch1Lvl1Case2 branch1Lvl1Case2) {
        this.id = id;
        this.bbranchName = bbranchName;
        this.branch1Lvl1Case2 = branch1Lvl1Case2;
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

    public Set<HBranch1Lvl3Case2> gethBranch1Lvl3Case2s() {
        return hBranch1Lvl3Case2s;
    }

    public void sethBranch1Lvl3Case2s(Set<HBranch1Lvl3Case2> hBranch1Lvl3Case2s) {
        this.hBranch1Lvl3Case2s = hBranch1Lvl3Case2s;
    }

    public IBranch1Lvl3Case2 getiBranch1Lvl3Case2() {
        return iBranch1Lvl3Case2;
    }

    public void setiBranch1Lvl3Case2(IBranch1Lvl3Case2 iBranch1Lvl3Case2) {
        this.iBranch1Lvl3Case2 = iBranch1Lvl3Case2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BBranch1Lvl2Case2)) return false;
        BBranch1Lvl2Case2 that = (BBranch1Lvl2Case2) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(bbranchName, that.bbranchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bbranchName);
    }

    @Override
    public String toString() {
        return "BBranch1Lvl2Case2{" +
                "id=" + id +
                ", bbranchName='" + bbranchName + '\'' +
                '}';
    }
}
