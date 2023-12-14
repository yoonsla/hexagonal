package com.example.blog.core.adpter.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordCommand {

    private String email;
    private String password;
    private String passwordCode;
}
