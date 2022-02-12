package com.github.mapper.case2.alpha;

import java.util.Objects;

public class ABranch1Lvl3Case2 {

    private Long id;

    private String currentName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABranch1Lvl3Case2)) return false;
        ABranch1Lvl3Case2 that = (ABranch1Lvl3Case2) o;
        return id == that.id && Objects.equals(currentName, that.currentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentName);
    }

    @Override
    public String toString() {
        return "ABranch1Lvl3Case2{" +
                "id=" + id +
                ", currentName='" + currentName + '\'' +
                '}';
    }
}
