package com.example.blog.domain;

import java.time.LocalDateTime;

public record Post(
    Long id,
    String subject,
    String content,
    String writer,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static Post ofNotExist() {
        return new Post(null, null, null, null, null, null);
    }

    public boolean notExist() {
        return this.id == null;
    }

    public static Post fromCreated(CreatePost createPost) {
        return new Post(null, createPost.subject(), createPost.content(), createPost.writer(), LocalDateTime.now(), LocalDateTime.now());
    }

    public static Post of(Long id, String subject, String content, String writer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(id, subject, content, writer, createdAt, updatedAt);
    }

    public boolean isNotWriter(String writer) {
        return !this.writer.equals(writer);
    }

    public Post updatePost(UpdatePost updatePost) {
        return new Post(id, updatePost.subject(), updatePost.content(), writer, LocalDateTime.now(), LocalDateTime.now());
    }
}
