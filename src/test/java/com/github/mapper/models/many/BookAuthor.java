package com.github.mapper.models.many;

import java.util.Objects;

public class BookAuthor {

    private Long authorId;

    private Long bookId;

    private Author author;

    private Book book;

    public BookAuthor() {
    }

    public BookAuthor(Author author, Book book) {
        this.author = author;
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthor)) return false;
        BookAuthor that = (BookAuthor) o;
        return Objects.equals(authorId, that.authorId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, bookId);
    }

    @Override
    public String toString() {
        return "BookAuthor{" +
                "authorId=" + authorId +
                ", bookId=" + bookId +
                '}';
    }
}
