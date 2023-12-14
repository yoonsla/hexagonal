package com.example.blog.core.service.port;

import com.example.blog.core.adpter.command.LoginCommand;
import com.example.blog.core.service.dto.RsaDto;
import com.example.blog.core.service.dto.UserWithTokenDto;

public interface LoginUseCase {

    UserWithTokenDto login(LoginCommand request);

    RsaDto getPublicKey();

    void logout(String email);
}
