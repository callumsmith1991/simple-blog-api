package com.example.simple_blog_rest_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePostRequest {

    @NotBlank
    private String content;

    @NotBlank
    private String title;

    @NotNull
    private Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
