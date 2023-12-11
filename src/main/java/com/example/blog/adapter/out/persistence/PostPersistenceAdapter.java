package com.example.blog.adapter.out.persistence;

import com.example.blog.domain.Post;
import com.example.blog.adapter.out.persistence.entity.PostEntity;
import com.example.blog.adapter.out.persistence.entity.PostRepository;
import com.example.blog.application.port.out.CommandPostPort;
import com.example.blog.application.port.out.QueryPostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostPersistenceAdapter implements CommandPostPort, QueryPostPort {

    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(PostEntity.from(post)).toDomain();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
            .map(PostEntity::toDomain)
            .orElse(Post.ofNotExist());
    }
}
