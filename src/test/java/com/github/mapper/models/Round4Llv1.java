package com.github.mapper.models;

import java.util.Objects;

public class Round4Llv1 {

    private Long id;

    private Integer count;

    private Integer amount;

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
