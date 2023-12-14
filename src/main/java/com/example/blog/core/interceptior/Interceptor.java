package com.example.blog.core.interceptior;

import static com.example.blog.core.contants.Constants.HEADER_AUTHORIZATION;

import com.example.blog.core.annotation.dto.AnnotationDto;
import com.example.blog.core.client.ParseTokenClaim;
import com.example.blog.core.contants.ResponseCode;
import com.example.blog.core.exception.BlogException;
import com.example.blog.core.infrastructure.dto.AccessClaim;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Configuration
@RequiredArgsConstructor
public class Interceptor implements AsyncHandlerInterceptor {

    private final HttpServletRequest httpRequest;
    private final ParseTokenClaim parseTokenClaim;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AnnotationDto annotationDto = new AnnotationDto(method);
        authenticate(annotationDto);
        return true;
    }

    private void authenticate(AnnotationDto annotationDto) {
        if (annotationDto.isAdminCallable()) {
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

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod) {
            log(LogManager.getLogger(((HandlerMethod) handler).getBeanType().getName()), request);
        }
    }

    public void log(Logger logger, HttpServletRequest request) {
        String method = String.format("%-6s", request.getMethod());
        logger.info("[{}] {}", method, request.getRequestURI());
    }
}
