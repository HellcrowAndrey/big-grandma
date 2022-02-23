package com.github.mapper.case2;

import com.github.mapper.case2.alpha.*;
import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.SubGraph;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependenciesGraphCase2Test {

    @Test
    void dependenciesGraphCase2() {

        DependenciesGraph.Root root = new DependenciesGraph.Root(
                RootCase2.class, // 0
                List.of(
                        new SubGraph.DefaultBuilder() // 1
                                .rootType(RootCase2.class)
                                .currentType(Branch1Lvl1Case2.class)
                                .rootFieldName("branch1Lvl1Case2s")
                                .currentFieldName("rootCase2")
                                .collType(List.class)
                                .graphs(List.of(
                                        new SubGraph.DefaultBuilder() // 2
                                                .rootType(Branch1Lvl1Case2.class)
                                                .currentType(ABranch1Lvl2Case2.class)
                                                .rootFieldName("aBranch1Lvl2Case2s")
                                                .currentFieldName("branch1Lvl1Case2")
                                                .collType(List.class)
                                                .graphs(List.of(
                                                        new SubGraph.DefaultBuilder() // 3
                                                                .rootType(ABranch1Lvl2Case2.class)
                                                                .currentType(ABranch1Lvl3Case2.class)
                                                                .rootFieldName("bBranch1Lvl3Case2s")
                                                                .collType(Set.class)
                                                                .build(),
                                                        new SubGraph.DefaultBuilder()
                                                                .rootType(ABranch1Lvl2Case2.class)
                                                                .currentType(BBranch1Lvl3Case2.class)
                                                                .rootFieldName("bBranch1Lvl3Case2s")
                                                                .collType(Set.class)
                                                                .build() // 3
                                                )).build(),
                                        new SubGraph.DefaultBuilder() // 2
                                                .rootType(Branch1Lvl1Case2.class)
                                                .currentType(BBranch1Lvl2Case2.class)
                                                .rootFieldName("branch1Lvl2Case2s")
                                                .currentFieldName("branch1Lvl1Case2")
                                                .collType(List.class)
                                                .graphs(List.of(
                                                        new SubGraph.DefaultBuilder()  // 3
                                                                .rootType(BBranch1Lvl2Case2.class)
                                                                .currentType(HBranch1Lvl3Case2.class)
                                                                .rootFieldName("hBranch1Lvl3Case2s")
                                                                .collType(Set.class)
                                                                .build(),
                                                        new SubGraph.DefaultBuilder()  // 3
                                                                .rootType(BBranch1Lvl2Case2.class)
                                                                .currentType(IBranch1Lvl3Case2.class)
                                                                .rootFieldName("iBranch1Lvl3Case2")
                                                                .build()
                                                )).build()
                                )).build(),

                        new SubGraph.DefaultBuilder() // 1
                                .rootType(RootCase2.class)
                                .currentType(Branch2Lvl1Case2.class)
                                .rootFieldName("branch2Lvl1Case2s")
                                .currentFieldName("rootCase2")
                                .collType(List.class)
                                .graphs(List.of(
                                        new SubGraph.DefaultBuilder() // 2
                                                .rootType(Branch2Lvl1Case2.class)
                                                .currentType(ABranch2Lvl2Case2.class)
                                                .rootFieldName("aBranch2Lvl2Case2s")
                                                .collType(List.class)
                                                .build(),
                                        new SubGraph.DefaultBuilder() // 2
                                                .rootType(Branch2Lvl1Case2.class)
                                                .currentType(BBranch2Lvl2Case2.class)
                                                .rootFieldName("bBranch2Lvl2Case2s")
                                                .collType(Set.class)
                                                .build()
                                )).build()

                )
        );

        DependenciesGraph dependencies = new DependenciesGraph(root);

        dependencies.many(data())
                .collectList()
                .subscribe(r -> System.out.println(r));

    }

    static List<Map<String, Object>> data() {
        Map<String, Object> round_1_entity_1 = new HashMap<>();

        Map<String, Object> round_1_entity_2 = new HashMap<>();

        Map<String, Object> round_1_entity_3 = new HashMap<>();

        Map<String, Object> round_2_entity_1 = new HashMap<>();

        Map<String, Object> round_2_entity_2 = new HashMap<>();

        Map<String, Object> round_2_entity_3 = new HashMap<>();

        return List.of(
                round_1_entity_1, round_1_entity_2, round_1_entity_3,
                round_2_entity_1, round_2_entity_2, round_2_entity_3
        );
    }

}
