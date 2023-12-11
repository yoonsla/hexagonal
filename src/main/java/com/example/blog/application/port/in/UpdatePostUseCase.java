package com.example.blog.application.port.in;

import com.example.blog.domain.Post;
import com.example.blog.adapter.in.command.UpdatePostCommand;

public interface UpdatePostUseCase {

    Post update(Long id, String email, UpdatePostCommand request);
}
