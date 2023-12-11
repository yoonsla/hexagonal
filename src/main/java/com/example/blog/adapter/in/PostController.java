package com.example.blog.adapter.in;

import com.example.blog.adapter.in.command.CreatePostCommand;
import com.example.blog.adapter.in.command.UpdatePostCommand;
import com.example.blog.adapter.in.response.PostResponse;
import com.example.blog.application.port.in.CreatePostUseCase;
import com.example.blog.application.port.in.GetPostUseCase;
import com.example.blog.application.port.in.UpdatePostUseCase;
import com.example.blog.core.adpter.response.Response;
import com.example.blog.core.contants.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Posts")
@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;

    @PostMapping
    public Response<PostResponse> create(@RequestBody CreatePostCommand request) {
        return Response.from(PostResponse.from(createPostUseCase.create(request.getSubject(), request.getContent(), request.getWriter())));
    }

    @GetMapping("/{id}")
    public Response<PostResponse> get(@PathVariable Long id) {
        return Response.from(PostResponse.from(getPostUseCase.get(id)));
    }

    @PutMapping("/{id}")
    public Response<PostResponse> update(
        @RequestHeader(Constants.USER_EMAIL) String email,
        @RequestBody UpdatePostCommand request,
        @PathVariable Long id
    ) {
        return Response.from(PostResponse.from(updatePostUseCase.update(id, email, request)));
    }
}
