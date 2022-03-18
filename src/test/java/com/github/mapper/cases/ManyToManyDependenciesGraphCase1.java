package com.github.mapper.cases;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.models.many.to.many.case1.RootManyToMany;
import com.github.mapper.models.many.to.many.case1.RoundLeft;

import java.util.List;

public class ManyToManyDependenciesGraphCase1 {

    public static DependenciesGraph graph() {
        DependenciesGraph.Root root = new DependenciesGraph.Root(
                RootManyToMany.class,
                List.of(
                        new SubGraph.ManyToManyBuilder()
                                .rootType(RootManyToMany.class)
                                .rootFieldName("roundLefts")
                                .currentType(RoundLeft.class)
                                .currentFieldName("roundRight")
                                .currentCollType(List.class)
                                .build()
                )
        );

        return new DependenciesGraph(root);
    }

}
