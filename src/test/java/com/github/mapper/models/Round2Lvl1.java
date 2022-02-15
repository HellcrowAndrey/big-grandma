package com.github.mapper.models;

import java.util.Objects;

public class Round2Lvl1 {

    private Long id;

    private boolean isFlag;

    private int tag;

    private String name;

    private RootLvl rootLvl;

    public Round2Lvl1() {
    }

    public Round2Lvl1(Long id) {
        this.id = id;
    }

    public Round2Lvl1(Long id, boolean isFlag, int tag, String name) {
        this.id = id;
        this.isFlag = isFlag;
        this.tag = tag;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public int getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public RootLvl getRootLvl() {
        return rootLvl;
    }

    public void setRootLvl(RootLvl rootLvl) {
        this.rootLvl = rootLvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round2Lvl1)) return false;
        Round2Lvl1 that = (Round2Lvl1) o;
        return isFlag == that.isFlag &&
                tag == that.tag &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isFlag, tag, name);
    }

    @Override
    public String toString() {
        return "Round2Lvl1{" +
                "id=" + id +
                ", isFlag=" + isFlag +
                ", tag=" + tag +
                '}';
    }
}
