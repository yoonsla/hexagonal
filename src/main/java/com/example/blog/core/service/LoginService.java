package com.example.blog.core.service;

import com.example.blog.application.port.out.QueryUserPort;
import com.example.blog.core.adpter.command.LoginCommand;
import com.example.blog.core.client.PasswordEncoder;
import com.example.blog.core.client.RsaService;
import com.example.blog.core.client.TokenStore;
import com.example.blog.core.client.repository.GenerateToken;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.infrastructure.dto.RefreshClaim;
import com.example.blog.core.infrastructure.dto.RsaCacheKey;
import com.example.blog.core.infrastructure.dto.RsaKeyPair;
import com.example.blog.core.service.dto.RsaDto;
import com.example.blog.core.service.dto.UserWithTokenDto;
import com.example.blog.core.service.port.LoginUseCase;
import com.example.blog.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final QueryUserPort queryUserPort;
    private final RsaService rsaService;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final GenerateToken generateToken;

    @Override
    public RsaDto getPublicKey() {
        RsaKeyPair rsaKeyPair = rsaService.getRsaData(RsaCacheKey.NEW_CACHE_KEY);
        return new RsaDto(rsaKeyPair.getPublicKeyStr(), rsaKeyPair.getModulusStr(), rsaKeyPair.getExponentStr());
    }

    @Override
    public UserWithTokenDto login(LoginCommand request) {
        final User user = queryUserPort.findByEmail(request.getEmail());
        if (user.notExist()) {
            throw new BlogException(ResponseCode.IS_NOT_EXIST);
        }
        checkValidPassword(user, rsaService.decryptRSA(request.getPassword()));
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        return UserWithTokenDto.of(accessToken, refreshToken, user);
    }

    @Override
    public void logout(String email) {
        tokenStore.delete(email);
    }

    private String generateAccessToken(User user) {
        AccessClaim accessClaim = AccessClaim.from(user);
        String accessToken = generateToken.accessToken(accessClaim);
        tokenStore.saveAccessToken(user.email(), accessToken);
        return accessToken;
    }

    private String generateRefreshToken(User user) {
        RefreshClaim refreshClaim = RefreshClaim.from(user);
        String refreshToken = generateToken.refreshToken(refreshClaim);
        tokenStore.saveRefreshToken(user.email(), refreshToken);
        return refreshToken;
    }

    private void checkValidPassword(User user, String password) {
        if (user.isNotEqualPassword(passwordEncoder, password)) {
            throw new BlogException(ResponseCode.NOT_SAME_PASSWORD);
        }
    }
}
