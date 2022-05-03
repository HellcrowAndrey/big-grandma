package com.github.mapper.sql;

import com.github.mapper.graph.Root;
import com.github.mapper.models.RootLvl;
import com.github.mapper.models.Round1Lvl1;
import com.github.mapper.models.Round2Lvl1;
import com.github.mapper.sql.key.worlds.KeyWorld;
import com.github.mapper.sql.key.worlds.SelectDefault;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class SelectTest {

    @Test
    public void mainTest() throws CloneNotSupportedException {
//        String[] columns = {
//                "firstName as fn",
//                "lastName as ln",
//                "age as aliasAge"
//        };
//        var tableName = "testTable";
//        SQLSelect result = SQLSelect.select(columns)
//                .from(tableName)
//                .toSelect();
//        System.out.println("Case 1 " + result.asString());
//        String result1 = SQLSelect.select(columns)
//                .from(tableName)
//                .toString();
//        System.out.println(result1);
//        SQLSelect result3 = SQLSelect.select(columns)
//                .from(tableName)
//                .orderBy(SortedType.desc, "lastName")
//                .orderBy("age", SortedType.asc)
//                .toSelect();
//        System.out.println(result3.asString());
//        SQLSelect result4 = SQLSelect.select(columns)
//                .from("testTable")
//                .join("testTable_2",
//                        ColumnName.columnName("t_2_id"),
//                        ColumnName.columnName("t_2_id")
//                ).join("testTable_3",
//                        ColumnName.columnName("t_3_id"),
//                        ColumnName.columnName("t_3_id")
//                ).where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                ).groupBy("firstName")
//                .orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println(result4.asString());
//        SQLSelect result5 = SQLSelect.select(columns)
//                .distinct()
//                .from(tableName)
//                .where(
//                        SQLCondition.column("age")
//                                .greaterThan(1)
//                                .and()
//                                .column("lastName")
//                                .eq("yes")
//                                .get()
//                ).groupBy("firstName")
//                .orderBy(SortedType.asc, "lastName")
//                .limit(10)
//                .offset(20)
//                .toSelect();
//        System.out.println("SQL -> " + result5.asString());
//        SQLSelect result6 = SQLSelect.select(columns)
//                .from("testTable")
//                .leftJoin("testTable_2", ColumnName.columnName("t_2_id"), ColumnName.columnName("t_2_id"))
//                .leftJoin("testTable_3", ColumnName.columnName("t_3_id"), ColumnName.columnName("t_3_id"))
//                .where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                ).orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println(result6.asString());
//        SQLSelect result7 = SQLSelect.select(columns)
//                .top(50)
//                .from("testTable")
//                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
//                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
//                .where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                ).orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println(result7.asString());
//
//        SQLSelect result8 = SQLSelect.select(columns)
//                .from("testTable")
//                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
//                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
//                .where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                ).orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println("Max -> " + result8.asString());
//
//        SQLSelect result9 = SQLSelect.select(columns)
//                .from("testTable")
//                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
//                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
//                .where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                ).orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println("Min -> " + result9.asString());
//
//        SQLSelect result10 = SQLSelect.select(columns)
//                .from("testTable")
//                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
//                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
//                .where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                ).orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println("Sum -> " + result10.asString());
//
//        SQLSelect result11 = SQLSelect.select(columns)
//                .from("testTable")
//                .leftJoin("testTable_2", "testTable.t_2_id", "testTable_2.t_2_id")
//                .leftJoin("testTable_3", "testTable.t_3_id", "testTable_3.t_3_id")
//                .where(SQLCondition.column("age")
//                        .greaterThan(1)
//                        .get()
//                )
//                .groupBy("lastName")
//                .having(SQLHaving
//                        .count("age")
//                        .eq(10)
//                        .and()
//                        .max("age")
//                        .lessThan(40)
//                        .ofCondition()
//                ).orderBy(SortedType.asc, "lastName")
//                .toSelect();
//        System.out.println("Result 11 -> " + result11.asString());
//        //*************************************************
//        SQLCondition con = SQLCondition.column("age")
//                .eq(1)
//                .get();
//        System.out.println(con.asString());
//        SQLCondition con1 = SQLCondition.column("age")
//                .eq(1)
//                .and()
//                .not()
//                .column("lastName")
//                .eq("vasia")
//                .get();
//        System.out.println(con1.asString());
//        SQLCondition con2 = SQLCondition.column("age")
//                .eq(1)
//                .and()
//                .column("lastName")
//                .isNotNull()
//                .get();
//        System.out.println(con2.asString());
//        String str3 = SQLCondition.column("age")
//                .between(1)
//                .and(20)
//                .toString();
//        System.out.println(str3);
//
//        String str4 = SQLCondition.column("age")
//                .greaterThan(1)
//                .and()
//                .column("lastName")
//                .eq("Tigr")
//                .toString();
//        System.out.println(str4);

        SQLSelect select = SQLSelect.select()
                .from(RootLvl.class)
                .join(Round1Lvl1.class, "id", "root_lvl_id")
                .leftJoin(RootLvl.class, Round2Lvl1.class, "id", "root_lvl_id")
                .where(SQLCondition.column("name").eq("vasia").get())
                .toSelect();

        System.out.println("New Select -> " + select.asString());

        ReactiveSelect select1 = SQLSelect.select()
                .from(RootLvl.class)
                .join(Round1Lvl1.class, "id", "root_lvl_id")
                .leftJoin(RootLvl.class, Round2Lvl1.class, "id", "root_lvl_id")
                .where(SQLCondition.column("name").eq("vasia").get())
                .toReactiveSelect(null);
        Mono<Object> r = select1.one();
        System.out.println(r);
    }

}