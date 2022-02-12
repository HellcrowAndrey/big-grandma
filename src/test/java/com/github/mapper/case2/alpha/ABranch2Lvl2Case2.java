package com.github.mapper.case2.alpha;

import java.util.Objects;

public class ABranch2Lvl2Case2 {

    private Long id;

    private String name;

    private int current;

    public ABranch2Lvl2Case2() {
    }

    public ABranch2Lvl2Case2(Long id, String name, int current) {
        this.id = id;
        this.name = name;
        this.current = current;
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

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABranch2Lvl2Case2)) return false;
        ABranch2Lvl2Case2 that = (ABranch2Lvl2Case2) o;
        return current == that.current &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, current);
    }

    @Override
    public String toString() {
        return "ABranch2Lvl2Case2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", current=" + current +
                '}';
    }
}
