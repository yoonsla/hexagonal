package com.example.blog.adapter.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostCommand {

    private String subject;
    private String content;
}
