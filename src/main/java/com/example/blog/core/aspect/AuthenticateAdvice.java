package com.example.blog.core.aspect;

import static com.example.blog.core.contants.Constants.HEADER_AUTHORIZATION;

import com.example.blog.core.annotation.dto.AnnotationDto;
import com.example.blog.core.client.ParseTokenClaim;
import com.example.blog.core.client.TokenStore;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.AccessClaim;
import com.example.blog.core.service.dto.AccessToken;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
@Aspect
@Order
public class AuthenticateAdvice {

    private final HttpServletRequest httpRequest;
    private final ParseTokenClaim parseTokenClaim;
    private final TokenStore tokenStore;

    @Before("com.example.blog.core.aspect.Pointcuts.handler()")
    public void checkBeforeController(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AnnotationDto annotationDto = new AnnotationDto(method);
        authenticate(annotationDto);
    }

    private void authenticate(AnnotationDto annotationDto) {
        if (annotationDto.isAnonymousCallable()) {
            return;
        }
        String accessToken = httpRequest.getHeader(HEADER_AUTHORIZATION);
        AccessClaim accessClaim = parseTokenClaim.accessToken(accessToken);
        // token 유효성 검사
        checkTokenValid(accessToken, accessClaim);
        // 유저 유효성 검사
        checkUserValid(annotationDto, accessClaim);
    }

    private void checkTokenValid(String accessToken, AccessClaim accessClaim) {
        // token 여부 검사
        if (!StringUtils.hasText(accessToken)) {
            throw new BlogException(ResponseCode.INVALID_ACCESS_TOKEN);
        }
        // 토큰이 만료 검사
        if (parseTokenClaim.isTokenExpired(accessToken)) {
            throw new BlogException(ResponseCode.INVALID_ACCESS_TOKEN);
        }
        // redis 저장 정보와 header token 이 같은지 비교
        AccessToken redisToken = tokenStore.getToken(accessClaim.getEmail());
        if (!redisToken.getToken().equals(accessToken)) {
            throw new BlogException(ResponseCode.INVALID_ACCESS_TOKEN);
        }
    }

    private void checkUserValid(AnnotationDto annotationDto, AccessClaim accessClaim) {
        boolean isAdmin = annotationDto.isAdminCallable();
        if (isAdmin && accessClaim.getRole().isUser()) {
            throw new BlogException(ResponseCode.USER_ROLE_MISMATCH);
        }
        setAttribute(accessClaim);
    }

    private void setAttribute(AccessClaim accessClaim) {
        httpRequest.setAttribute("id", accessClaim.getId());
        httpRequest.setAttribute("email", accessClaim.getName());
        httpRequest.setAttribute("name", accessClaim.getEmail());
        httpRequest.setAttribute("role", accessClaim.getRole());
    }
}
