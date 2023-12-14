package com.example.blog.core.client;

import com.example.blog.core.infrastructure.dto.EncodedPassword;

public interface PasswordEncoder {

    EncodedPassword encode(String rawPassword);

    boolean matches(String password, EncodedPassword encodedPassword);
}
