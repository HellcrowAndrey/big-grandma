package com.github.mapper.case2.alpha;

import java.util.Objects;

public class HBranch1Lvl3Case2 {

    private Long id;

    private boolean isFlag;

    public HBranch1Lvl3Case2() {
    }

    public HBranch1Lvl3Case2(Long id, boolean isFlag) {
        this.id = id;
        this.isFlag = isFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HBranch1Lvl3Case2)) return false;
        HBranch1Lvl3Case2 that = (HBranch1Lvl3Case2) o;
        return isFlag == that.isFlag && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFlag);
    }

    @Override
    public String toString() {
        return "HBranch1Lvl3Case2{" +
                "id=" + id +
                ", isFlag=" + isFlag +
                '}';
    }
}
