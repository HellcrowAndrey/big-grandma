package com.github.mapper.case2.alpha;

import java.util.Objects;

public class IBranch1Lvl3Case2 {

    private Long id;

    private int current;

    public IBranch1Lvl3Case2() {
    }

    public IBranch1Lvl3Case2(Long id, int current) {
        this.id = id;
        this.current = current;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IBranch1Lvl3Case2)) return false;
        IBranch1Lvl3Case2 that = (IBranch1Lvl3Case2) o;
        return current == that.current && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, current);
    }

    @Override
    public String toString() {
        return "IBranch1Lvl3Case2{" +
                "id=" + id +
                ", current=" + current +
                '}';
    }
}
