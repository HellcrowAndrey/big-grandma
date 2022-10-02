package com.github.mapper.sql.pipeline;

import com.github.mapper.StringSqlUtils;
import com.github.mapper.sql.QueryContext;
import com.github.mapper.sql.SortedType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SelectTemplatePipeline {

    protected static final String SELECT_PATTERN = "select";

    protected static final String DISTINCT = "distinct";

    protected static final String TOP = "top %d";

    protected static final String FROM = "from %s";

    protected static final String FROM_AND_ALIAS = "from %s as %s";

    protected static final String JOIN = "inner join %s on %s = %s";

    protected static final String JOIN_WITH_ALIAS = "inner join %s as %s on %s = %s";

    protected static final String LEFT_JOIN = "left join %s on %s = %s";

    protected static final String LEFT_JOIN_WITH_ALIAS = "left join %s as %s on %s = %s";

    protected static final String WHERE_PATTERN = "where %s";

    protected static final String GROUP_BY = "group by %s";

    protected static final String HAVING = "having %s";

    protected static final String ORDER_BY = "order by %s %s";

    protected static final String COMA_BY = ", %s %s";

    protected static final String LIMIT = "limit %d";

    protected static final String OFFSET = "offset %d";

    protected static final String AS = "as";

    protected final QueryContext queryContext;

    protected final List<String> partsOfQuery;

    protected int orderByCount = 0;

    public SelectTemplatePipeline(QueryContext queryContext, List<String> partsOfQuery) {
        this.queryContext = queryContext;
        this.partsOfQuery = partsOfQuery;
        this.partsOfQuery.add(SELECT_PATTERN);
    }

    protected String switchOperatorOrderBy(SortedType sortedType, String... columns) {
        String result;
        Object tmp = columns.length > 1 ? StringSqlUtils.toStringSeparatorComa(columns) : columns[0];
        if (this.orderByCount++ == 0) {
            result = String.format(ORDER_BY, tmp, sortedType.name());
        } else {
            result = String.format(COMA_BY, tmp, sortedType.name());
        }
        return result;
    }

    protected String toJoin(String joinType, Class<?> toJoin, Class<?> toTable, String columnToJoin, String columnToTable) {
        this.queryContext.addTable(toJoin, toTable);
        QueryContext.Table toJoinTbl = this.queryContext.getTable(toJoin);
        var toJoinTblName = toJoinTbl.getName();
        var toJoinTblAlias = toJoinTbl.getAlias();
        QueryContext.Table toTbl = this.queryContext.getTable(toTable);
        var toTblAlias = toTbl.getAlias();
        return String.format(
                joinType,
                toJoinTblName,
                toJoinTblAlias,
                String.format("%s.%s", toJoinTblAlias, columnToJoin),
                String.format("%s.%s", toTblAlias, columnToTable)
        );
    }

    protected List<String> aggregateColumns() {
        Map<QueryContext.Table, List<QueryContext.Column>> columns = this.queryContext.getColumns();
        return columns.keySet().stream()
                .flatMap(table -> columns.get(table).stream())
                .map(column -> String.join(StringSqlUtils.SPACE, column.getColumnName(), AS, column.getAlias()))
                .collect(Collectors.toList());
    }

}
