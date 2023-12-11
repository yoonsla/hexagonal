package com.example.blog.adapter.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePostCommand {

    private String subject;
    private String content;
    private String writer;
}
