package com.github.mapper.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Round3Llv1 {

    private Long id;

    private String  roundName;

    private byte[] array;

    private Set<Round4Llv1> round4Llv1s = new HashSet<>();

    public Round3Llv1() {
    }

    public Round3Llv1(Long id, String roundName, byte[] array) {
        this.id = id;
        this.roundName = roundName;
        this.array = array;
    }

    public Long getId() {
        return id;
    }

    public String getRoundName() {
        return roundName;
    }

    public byte[] getArray() {
        return array;
    }

    public Set<Round4Llv1> getRound4Llv1s() {
        return round4Llv1s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round3Llv1)) return false;
        Round3Llv1 that = (Round3Llv1) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(roundName, that.roundName) &&
                Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, roundName);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return "Round3Llv1{" +
                "id=" + id +
                ", roundName='" + roundName + '\'' +
                ", array=" + Arrays.toString(array) +
                '}';
    }
}
