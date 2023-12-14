package com.example.blog.application.port.out;

import com.example.blog.domain.UserPasswordAuth;

public interface QueryUserPasswordAuthPort {

    UserPasswordAuth findByLastAuthCode(String email);
}
