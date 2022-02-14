package com.github.mapper.models;

import java.util.Objects;

public class Round1Lvl1 {

    private Long id;

    private String title;

    private Integer amount;

    private RootLvl rootLvl;

    public Round1Lvl1() {
    }

    public Round1Lvl1(Long id) {
        this.id = id;
    }

    public Round1Lvl1(Long id, String title, Integer amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getAmount() {
        return amount;
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
        if (!(o instanceof Round1Lvl1)) return false;
        Round1Lvl1 that = (Round1Lvl1) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, amount);
    }

    @Override
    public String toString() {
        return "Round1Lvl1{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                '}';
    }
}
