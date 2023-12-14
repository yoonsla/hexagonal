package com.example.blog.core.interceptior;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class Interceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
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
