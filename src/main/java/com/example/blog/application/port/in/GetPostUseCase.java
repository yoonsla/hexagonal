package com.example.blog.application.port.in;

import com.example.blog.domain.Post;

public interface GetPostUseCase {

    Post get(Long id);
}
