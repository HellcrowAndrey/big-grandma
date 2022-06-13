package com.github.mapper.graph.cases.impl;

import com.github.mapper.graph.DependenciesGraph;
import com.github.mapper.graph.Root;
import com.github.mapper.graph.SubGraph;
import com.github.mapper.graph.cases.DependenciesGraphParameterized;
import com.github.mapper.models.many.Author;
import com.github.mapper.models.many.Book;
import com.github.mapper.models.many.BookAuthor;

import java.util.*;

public class ManyToManyAuthorBook implements DependenciesGraphParameterized {

    @Override
    public List<Map<String, Object>> tuples() {
        Map<String, Object> row_1 = new HashMap<>();

        row_1.put("authorId", 1L);
        row_1.put("author", "a-1");
        row_1.put("author_Id_1", 1L);
        row_1.put("book_Id_1", 1L);
        row_1.put("bookId", 1L);
        row_1.put("bookName", "b-1");

        Map<String, Object> row_2 = new HashMap<>();

        row_2.put("authorId", 1L);
        row_2.put("author", "a-1");
        row_2.put("author_Id_1", 1L);
        row_2.put("book_Id_1", 2L);
        row_2.put("bookId", 2L);
        row_2.put("bookName", "b-2");

        Map<String, Object> row_3 = new HashMap<>();

        row_3.put("authorId", 1L);
        row_3.put("author", "a-1");
        row_3.put("author_Id_1", 1L);
        row_3.put("book_Id_1", 3L);
        row_3.put("bookId", 3L);
        row_3.put("bookName", "b-3");

        Map<String, Object> row_4 = new HashMap<>();

        row_4.put("authorId", 2L);
        row_4.put("author", "a-2");
        row_4.put("author_Id_1", 2L);
        row_4.put("book_Id_1", 3L);
        row_4.put("bookId", 3L);
        row_4.put("bookName", "b-3");

        Map<String, Object> row_5 = new HashMap<>();

        row_5.put("authorId", 3L);
        row_5.put("author", "a-3");
        row_5.put("author_Id_1", 3L);
        row_5.put("book_Id_1", 2L);
        row_5.put("bookId", 2L);
        row_5.put("bookName", "b-2");

        return List.of(row_1, row_2, row_3, row_4, row_5);
    }

    @Override
    public List<Map<String, Object>> tuple() {

        Map<String, Object> row_1 = new HashMap<>();

        row_1.put("authorId", 1L);
        row_1.put("author", "a-1");
        row_1.put("author_Id_1", 1L);
        row_1.put("book_Id_1", 1L);
        row_1.put("bookId", 1L);
        row_1.put("bookName", "b-1");

        return List.of(row_1);
    }

    @Override
    public DependenciesGraph graph() {
        Root root = new Root.Builder()
                .rootType(Author.class)
                .graphOneToEtc(
                        new SubGraph.Builder()
                                .rootType(Author.class)
                                .rootFieldName("books")
                                .rootCollType(Set.class)
                                .currentType(BookAuthor.class)
                                .currentFieldName("author")
                                .alias("author_Id_1", "authorId")
                                .alias("book_Id_1", "bookId")
                                .graphOneToEtc(
                                        new SubGraph.Builder()
                                                .rootType(BookAuthor.class)
                                                .rootFieldName("book")
                                                .currentType(Book.class)
                                                .graphOneToEtc(
                                                        new SubGraph.Builder()
                                                                .rootType(Book.class)
                                                                .rootFieldName("authors")
                                                                .rootCollType(List.class)
                                                                .currentType(BookAuthor.class)
                                                                .alias("author_Id_1", "authorId")
                                                                .alias("book_Id_1", "bookId")
                                                                .build()
                                                )
                                                .alias("bookId", "id")
                                                .alias("bookName", "name")
                                                .build()
                                )
                                .build()
                ).alias("authorId", "id")
                .alias("author", "author")
                .build();
        return new DependenciesGraph(root);
    }

    @Override
    public List<Object> expect_many() {
        return new ArrayList<>();
    }

    @Override
    public Object expect_one() {
        return new ArrayList<>();
    }
}
