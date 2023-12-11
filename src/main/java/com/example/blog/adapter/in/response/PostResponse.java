package com.example.blog.adapter.in.response;

import com.example.blog.domain.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostResponse {

    private Long id;
    private String subject;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponse from(Post post) {
        return new PostResponse(post.id(), post.subject(), post.content(), post.writer(), post.createdAt(), post.updatedAt());
    }
}
