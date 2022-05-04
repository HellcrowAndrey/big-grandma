package com.github.mapper.entityies;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(value = "comment_case_1")
public class Comment {

    @Id
    private Long id;

    private String reply;

    private Post post;

    private User user;

    public Comment() {
    }

    public Comment(Long id, String reply, User user) {
        this.id = id;
        this.reply = reply;
        this.user = user;
    }

    public Comment(Long id, String reply, Post post, User user) {
        this.id = id;
        this.reply = reply;
        this.post = post;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getReply() {
        return reply;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(reply, comment.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reply);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", reply='" + reply + '\'' +
//                ", post=" + post +
                ", user=" + user +
                '}';
    }
}
