package com.example.blog.core.adpter.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginCommand {

    private String email;
    private String password;
}
