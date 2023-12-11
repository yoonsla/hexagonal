package com.example.blog.application.port.in;

import com.example.blog.domain.Post;

public interface CreatePostUseCase {

    Post create(String subject, String content, String writer);
}
