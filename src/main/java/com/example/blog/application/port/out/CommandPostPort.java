package com.example.blog.application.port.out;

import com.example.blog.domain.Post;

public interface CommandPostPort {

    Post save(Post post);
}
