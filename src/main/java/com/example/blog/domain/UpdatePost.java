package com.example.blog.domain;

public record UpdatePost(String subject, String content) {

    public static UpdatePost of(String subject, String content) {
        return new UpdatePost(subject, content);
    }
}
