package com.github.mapper.case2.alpha;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*

- RootCase2 (0)

	- Branch1Lvl1Case2 (1)

		- ABranch1Lvl2Case2 (2)

			- ABranch1Lvl3Case2 (3)

			- BBranch1Lvl3Case2 (3)

		- BBranch1Lvl2Case2 (2)

			- HBranch1Lvl3Case2 (3)

			- IBranch1Lvl3Case2 (3)

	- Branch2Lvl1Case2 (1)

		- ABranch2Lvl2Case2 (2)

		- BBranch2Lvl2Case2 (2)


 */

public class RootCase2 {

    private Long id;

    private String name;

    private List<Branch1Lvl1Case2> branch1Lvl1Case2s = new ArrayList<>();

    private List<Branch2Lvl1Case2> branch2Lvl1Case2s = new ArrayList<>();

    public RootCase2() {
    }

    public RootCase2(Long id, String name, List<Branch1Lvl1Case2> branch1Lvl1Case2s) {
        this.id = id;
        this.name = name;
        this.branch1Lvl1Case2s = branch1Lvl1Case2s;
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

    public List<Branch1Lvl1Case2> getBranch1Lvl1Case2s() {
        return branch1Lvl1Case2s;
    }

    public void setBranch1Lvl1Case2s(List<Branch1Lvl1Case2> branch1Lvl1Case2s) {
        this.branch1Lvl1Case2s = branch1Lvl1Case2s;
    }

    public List<Branch2Lvl1Case2> getBranch2Lvl1Case2s() {
        return branch2Lvl1Case2s;
    }

    public void setBranch2Lvl1Case2s(List<Branch2Lvl1Case2> branch2Lvl1Case2s) {
        this.branch2Lvl1Case2s = branch2Lvl1Case2s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootCase2)) return false;
        RootCase2 rootCase2 = (RootCase2) o;
        return Objects.equals(id, rootCase2.id) &&
                Objects.equals(name, rootCase2.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RootCase2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", branch1Lvl1Case2s=" + branch1Lvl1Case2s +
                '}';
    }
}
