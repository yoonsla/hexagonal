package com.example.blog.application.port.in;

import com.example.blog.adapter.in.command.CreateUserCommand;
import com.example.blog.core.adpter.command.ChangePasswordCommand;

public interface UserUseCase {

    String regenerate(String accessToken, String refreshToken);

    void sendAuthCode(String email);

    void changePassword(ChangePasswordCommand request);

    void create(CreateUserCommand request);
}
