package com.github.mapper.models.many;

import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(value = "authors")
public class Author {

    private Long id;

    private String author;

    private Set<BookAuthor> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Set<BookAuthor> getBooks() {
        return books;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author1 = (Author) o;
        return Objects.equals(id, author1.id) &&
                Objects.equals(author, author1.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author);
    }
}
