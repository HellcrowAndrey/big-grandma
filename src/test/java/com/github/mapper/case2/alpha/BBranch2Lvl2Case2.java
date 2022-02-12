package com.github.mapper.case2.alpha;

import java.util.Objects;

public class BBranch2Lvl2Case2 {

    private Long id;

    private String newObject;

    private byte superByte;

    public BBranch2Lvl2Case2() {
    }

    public BBranch2Lvl2Case2(Long id, String newObject, byte superByte) {
        this.id = id;
        this.newObject = newObject;
        this.superByte = superByte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewObject() {
        return newObject;
    }

    public void setNewObject(String newObject) {
        this.newObject = newObject;
    }

    public byte getSuperByte() {
        return superByte;
    }

    public void setSuperByte(byte superByte) {
        this.superByte = superByte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BBranch2Lvl2Case2)) return false;
        BBranch2Lvl2Case2 that = (BBranch2Lvl2Case2) o;
        return superByte == that.superByte &&
                Objects.equals(id, that.id) &&
                Objects.equals(newObject, that.newObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newObject, superByte);
    }

    @Override
    public String toString() {
        return "BBranch2Lvl2Case2{" +
                "id=" + id +
                ", newObject='" + newObject + '\'' +
                ", superByte=" + superByte +
                '}';
    }
}
