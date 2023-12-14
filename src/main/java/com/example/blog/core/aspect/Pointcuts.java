package com.example.blog.core.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order
public class Pointcuts {

    @Pointcut("execution(* com.example.blog.*.adapter.*(..))")
    public void handler() {
    }

    @Pointcut("execution(* com.example.blog.*.service.*(..))")
    public void service() {
    }
}
