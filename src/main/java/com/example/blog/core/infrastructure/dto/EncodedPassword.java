package com.example.blog.core.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EncodedPassword {

    private String encodedPassword;
    private String salt;
}
