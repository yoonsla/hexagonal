package com.example.blog.mock;

import com.example.blog.adapter.out.persistence.entity.PostEntity;
import com.example.blog.domain.Post;
import com.example.blog.application.port.out.CommandPostPort;
import java.util.ArrayList;
import java.util.List;

public class FakeCommandPostPort implements CommandPostPort {

    final List<PostEntity> posts = new ArrayList<>();
    Long generateId = 0L;

    @Override
    public Post save(Post post) {
        if (post.id() != null) {
            // update
            posts.removeIf(v -> v.getId().equals(post.id()));
            Post updated = new Post(
                post.id(),
                post.subject(),
                post.content(),
                post.writer(),
                post.createdAt(),
                post.updatedAt()
            );
            posts.add(PostEntity.from(updated));
            return updated;
        } else {
            // save
            Post saved = new Post(
                ++generateId,
                post.subject(),
                post.content(),
                post.writer(),
                post.createdAt(),
                post.updatedAt()
            );
            posts.add(PostEntity.from(saved));
            return saved;
        }
    }
}
