package com.example.blog.core.aspect;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
@Order
@Log4j2
public class LogAdvice {

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

    @Around("com.example.blog.core.aspect.Pointcuts.handler()")
    public void beforeHandler(ProceedingJoinPoint joinPoint) {
        aroundLog(joinPoint, JoinPointKind.HANDLER);
    }

    @Around("com.example.blog.core.aspect.Pointcuts.service()")
    public void beforeService(ProceedingJoinPoint joinPoint) {
        aroundLog(joinPoint, JoinPointKind.SERVICE);
    }

    private void aroundLog(ProceedingJoinPoint joinPoint, JoinPointKind kind) {
        long start = System.currentTimeMillis();
        log.info(
            "@{} : {}.{} / startedAt : {}",
            kind,
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            timeFormat(new Timestamp(start))
        );
        log.info(" Args: {}", getArgsAsString(joinPoint.getArgs()));

        long end = System.currentTimeMillis();
        log.info(
            "@{} : {}.{} / endedAt : {}, executionTime: {} ms",
            kind,
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            timeFormat(new Timestamp(start)),
            (end - start)
        );
    }

    private String getArgsAsString(Object[] objects) {
        return Arrays.stream(objects)
            .map(obj -> Optional.ofNullable(obj)
                .map(v -> v.getClass().getSimpleName() + " :::: " + v)
                .orElse("? :::: null")
            ).collect(Collectors.joining(", "));
    }

    private String timeFormat(Timestamp timestamp) {
        final LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return format(localDateTime);
    }

    private String format(LocalDateTime localDateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LogAdvice.DEFAULT_DATETIME_FORMAT);
        return localDateTime.format(formatter);
    }
}
