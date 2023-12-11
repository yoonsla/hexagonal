package com.example.blog.application.service;

import com.example.blog.domain.UpdatePost;
import com.example.blog.adapter.in.command.UpdatePostCommand;
import com.example.blog.application.port.in.UpdatePostUseCase;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.domain.CreatePost;
import com.example.blog.domain.Post;
import com.example.blog.application.port.in.CreatePostUseCase;
import com.example.blog.application.port.in.GetPostUseCase;
import com.example.blog.application.port.out.CommandPostPort;
import com.example.blog.application.port.out.QueryPostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService implements CreatePostUseCase, GetPostUseCase, UpdatePostUseCase {

    private final CommandPostPort commandPostPort;
    private final QueryPostPort queryPostPort;

    @Override
    public Post create(String subject, String content, String writer) {
        final CreatePost createPost = CreatePost.of(subject, content, writer);
        final Post post = Post.fromCreated(createPost);
        return commandPostPort.save(post);
    }

    @Override
    public Post get(Long id) {
        final Post post = queryPostPort.findById(id);
        if (post.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST, " id : {" + id + "}");
        }
        return post;
    }

    @Override
    public Post update(Long id, String email, UpdatePostCommand request) {
        Post post = queryPostPort.findById(id);
        if (post.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST, " id : {" + id + "}");
        }
        if (post.isNotWriter(email)) {
            throw new BlogException(ResponseCode.IS_NOT_WRITER, "is not writer : {" + email + "}");
        }
        final UpdatePost updatePost = UpdatePost.of(request.getSubject(), request.getContent());
        post = post.updatePost(updatePost);
        commandPostPort.save(post);
        return post;
    }
}
