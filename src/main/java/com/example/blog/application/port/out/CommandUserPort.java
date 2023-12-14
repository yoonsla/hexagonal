package com.example.blog.application.port.out;

import com.example.blog.domain.User;

public interface CommandUserPort {

    User save(User user);
}
