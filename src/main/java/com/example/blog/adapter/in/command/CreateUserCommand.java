package com.example.blog.adapter.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserCommand {

    private String name;
    private String email;
    private String password;
}
