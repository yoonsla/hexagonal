package com.example.blog.core.adpter.exception;

import com.example.blog.core.adpter.response.Response;
import com.example.blog.core.exception.BlogException;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@Order(Integer.MAX_VALUE -1)
public class BlogExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public Response<Void> blogExceptionHandler(BlogException exception) {
        errorLog(exception);
        return Response.error(exception.getResponseCode());
    }

    private void errorLog(BlogException exception) {
        String stackTrace = Arrays.stream(exception.getStackTrace())
            .filter(v -> v.getClassName().startsWith("com.example.blog"))
            .map(v -> "\tat " + v)
            .collect(Collectors.joining("\n"));
        log.error("code: [{}] - message: {}, \n{}", exception.getResponseCode(), exception.getMessage(), stackTrace);
    }
}
