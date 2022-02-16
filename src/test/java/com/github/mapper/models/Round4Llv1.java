package com.github.mapper.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Round4Llv1 {

    private Long id;

    private Integer count;

    private Integer amount;

    private Set<Round3Llv1> round3Llv1s = new HashSet<>();

    public Round4Llv1() {
    }

    public Round4Llv1(Long id, Integer count, Integer amount) {
        this.id = id;
        this.count = count;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getAmount() {
        return amount;
    }

    public Set<Round3Llv1> getRound3Llv1s() {
        return round3Llv1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round4Llv1)) return false;
        Round4Llv1 that = (Round4Llv1) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(count, that.count) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, amount);
    }

    @Override
    public String toString() {
        return "Round4Llv1{" +
                "id=" + id +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }
}
