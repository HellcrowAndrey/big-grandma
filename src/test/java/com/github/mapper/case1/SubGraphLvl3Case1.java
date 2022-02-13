package com.github.mapper.case1;

import java.util.Objects;

public class SubGraphLvl3Case1 {

    private Long id;

    private Integer amount;

    private boolean isAccount;

    private SubGraphLvl2Case1 subGraphLvl2Case1;

    public SubGraphLvl3Case1() {
    }

    public SubGraphLvl3Case1(Long id, Integer amount, boolean isAccount, SubGraphLvl2Case1 subGraphLvl2Case1) {
        this.id = id;
        this.amount = amount;
        this.isAccount = isAccount;
        this.subGraphLvl2Case1 = subGraphLvl2Case1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isAccount() {
        return isAccount;
    }

    public void setAccount(boolean account) {
        isAccount = account;
    }

    public SubGraphLvl2Case1 getSubGraphLvl2Case1() {
        return subGraphLvl2Case1;
    }

    public void setSubGraphLvl2Case1(SubGraphLvl2Case1 subGraphLvl2Case1) {
        this.subGraphLvl2Case1 = subGraphLvl2Case1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubGraphLvl3Case1)) return false;
        SubGraphLvl3Case1 that = (SubGraphLvl3Case1) o;
        return isAccount == that.isAccount && Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(subGraphLvl2Case1, that.subGraphLvl2Case1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, isAccount, subGraphLvl2Case1);
    }

    @Override
    public String toString() {
        return "SubGraphLvl3Case1{" +
                "id=" + id +
                ", amount=" + amount +
                ", isAccount=" + isAccount +
                '}';
    }
}
