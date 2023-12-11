package com.example.blog.core.exception;

import com.example.blog.core.contants.ResponseCode;
import lombok.Getter;

@Getter
public class BlogException extends RuntimeException {

    private final ResponseCode responseCode;
    private String message;

    public BlogException(ResponseCode responseCode, String message) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.message = message;
    }

    public BlogException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
