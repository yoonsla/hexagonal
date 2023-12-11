package com.example.blog.mock;

import com.example.blog.adapter.out.persistence.entity.PostEntity;
import com.example.blog.domain.Post;
import com.example.blog.application.port.out.QueryPostPort;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FakeQueryPostPort implements QueryPostPort {

    List<PostEntity> posts = new ArrayList<>();

    @Override
    public Post findById(Long id) {
        Post saved = new Post(
            id,
            "test",
            "test22",
            "sy.kim@twolinecode.com",
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        posts.add(PostEntity.from(saved));
        return posts.stream()
            .filter(v -> v.getId().equals(id))
            .map(PostEntity::toDomain)
            .findAny()
            .orElse(Post.ofNotExist());
    }
}
