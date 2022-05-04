package com.github.mapper.entityies;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(value = "post_case_1")
public class Post {

    @Id
    private Long id;

    private String subject;

    private User user;

    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(Long id, String subject, User user) {
        this.id = id;
        this.subject = subject;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSubject() {
        return subject;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(subject, post.subject) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, user);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
