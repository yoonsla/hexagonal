package com.example.blog.domain;

public record CreatePost(String subject, String content, String writer) {

    public static CreatePost of(String subject, String content, String writer) {
        return new CreatePost(subject, content, writer);
    }
}
