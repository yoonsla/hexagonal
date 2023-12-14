package com.example.blog.core.aspect;

import static com.example.blog.core.contants.Constants.HEADER_AUTHORIZATION;

import com.example.blog.core.annotation.dto.AnnotationDto;
import com.example.blog.core.client.ParseTokenClaim;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.AccessClaim;
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
        // token 유효성 검사
        checkTokenValid(accessToken);
        // 유저 유효성 검사
        checkUserValid(annotationDto, accessToken);
    }

    private void checkTokenValid(String accessToken) {
        // token 여부 검사
        if (!StringUtils.hasText(accessToken)) {
            throw new BlogException(ResponseCode.INVALID_ACCESS_TOKEN);
        }
        // 토큰이 만료 검사
        if (parseTokenClaim.isTokenExpired(accessToken)) {
            throw new BlogException(ResponseCode.INVALID_ACCESS_TOKEN);
        }
    }

    private void checkUserValid(AnnotationDto annotationDto, String accessToken) {
        AccessClaim accessClaim = parseTokenClaim.accessToken(accessToken);
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
