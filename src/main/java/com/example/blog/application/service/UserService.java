package com.example.blog.application.service;

import com.example.blog.adapter.in.command.CreateUserCommand;
import com.example.blog.application.port.in.UserUseCase;
import com.example.blog.application.port.out.CommandUserPasswordAuthPort;
import com.example.blog.application.port.out.CommandUserPort;
import com.example.blog.application.port.out.QueryUserPasswordAuthPort;
import com.example.blog.application.port.out.QueryUserPort;
import com.example.blog.core.adpter.command.ChangePasswordCommand;
import com.example.blog.core.client.ParseTokenClaim;
import com.example.blog.core.client.PasswordEncoder;
import com.example.blog.core.client.RsaService;
import com.example.blog.core.client.TokenStore;
import com.example.blog.core.client.repository.GenerateToken;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.infrastructure.dto.EncodedPassword;
import com.example.blog.domain.CreateUser;
import com.example.blog.domain.User;
import com.example.blog.domain.UserPasswordAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final CommandUserPort commandUserPort;
    private final QueryUserPort queryUserPort;
    private final QueryUserPasswordAuthPort queryUserPasswordAuthPort;
    private final CommandUserPasswordAuthPort commandUserPasswordAuthPort;
    private final ParseTokenClaim parseTokenClaim;
    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;
    private final RsaService rsaService;
    private final GenerateToken generateToken;

    @Override
    public void create(CreateUserCommand request) {
        final EncodedPassword encodePassword = passwordEncoder.encode(rsaService.decryptRSA(request.getPassword()));

        final CreateUser createUser = CreateUser.of(
            request.getEmail(),
            request.getName(),
            encodePassword.getEncodedPassword(),
            encodePassword.getSalt()
        );
        User user = User.ofCreated(createUser);
        commandUserPort.save(user);
    }

    @Override
    public String regenerate(String accessToken, String refreshToken) {
        // refresh token 유효성 검사
        checkRefreshToken(refreshToken);
        // user 검사
        final AccessClaim accessClaim = parseTokenClaim.accessToken(accessToken);
        final User user = queryUserPort.findByEmail(accessClaim.getEmail());
        if (user.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST);
        }
        // 만족 시 재발급 및 redis 저장
        return generateAccessToken(user);
    }

    @Override
    public void sendAuthCode(String email) {
        final User user = queryUserPort.findByEmail(email);
        if (user.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST);
        }
        final String authCode = "123456";
        UserPasswordAuth passwordAuth = UserPasswordAuth.ofCreated(user.email(), authCode);
        commandUserPasswordAuthPort.save(passwordAuth);
    }

    @Override
    public void changePassword(ChangePasswordCommand request) {
        User user = queryUserPort.findByEmail(request.getEmail());
        if (user.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST);
        }
        final UserPasswordAuth userPasswordAuth = queryUserPasswordAuthPort.findByLastAuthCode(user.email());
        if (userPasswordAuth.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST);
        }
        if (userPasswordAuth.isOverTime()) {
            throw new BlogException(ResponseCode.IS_EXPIRED_PASSWORD_AUTH);
        }
        final EncodedPassword encodedPassword = passwordEncoder.encode(rsaService.decryptRSA(request.getPassword()));
        user = user.updatePassword(encodedPassword);
        queryUserPort.save(user);
    }

    private void checkRefreshToken(String refreshToken) {
        // 만료 체크
        if (parseTokenClaim.isTokenExpired(refreshToken)) {
            throw new BlogException(ResponseCode.INVALID_ACCESS_TOKEN);
        }
    }

    private String generateAccessToken(User user) {
        AccessClaim accessClaim = AccessClaim.from(user);
        String accessToken = generateToken.accessToken(accessClaim);
        tokenStore.saveAccessToken(user.email(), accessToken);
        return accessToken;
    }
}
