package com.example.blog.application.port.out;

import com.example.blog.domain.User;

public interface QueryUserPort {

    User findByEmail(String email);

    User save(User user);
}
