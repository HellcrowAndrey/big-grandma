package com.github.mapper.case2.alpha;

import java.util.Objects;

public class BBranch1Lvl3Case2 {

    private Long id;

    private String thisIsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThisIsName() {
        return thisIsName;
    }

    public void setThisIsName(String thisIsName) {
        this.thisIsName = thisIsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BBranch1Lvl3Case2)) return false;
        BBranch1Lvl3Case2 that = (BBranch1Lvl3Case2) o;
        return Objects.equals(id, that.id) && Objects.equals(thisIsName, that.thisIsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thisIsName);
    }

    @Override
    public String toString() {
        return "BBranch1Lvl3Case2{" +
                "id=" + id +
                ", thisIsName='" + thisIsName + '\'' +
                '}';
    }
}
