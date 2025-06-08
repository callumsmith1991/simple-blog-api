package com.example.simple_blog_rest_api.models;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue()
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    private String title;

    private String content;

    public String getNameOfPostUser()
    {
        return this.user.getFullName();
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
