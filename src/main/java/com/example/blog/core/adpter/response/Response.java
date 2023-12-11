package com.example.blog.core.adpter.response;

import com.example.blog.core.contants.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private final boolean success;
    private final int code;
    private final int httpStatusCode;
    private final String message;
    private final T result;

    public Response(ResponseCode responseCode, T result) {
        this.success = true;
        this.code = responseCode.getCode();
        this.httpStatusCode = responseCode.getHttpStatusCode();
        this.message = responseCode.getMessage();
        this.result = result;
    }

    public static Response<Void> ok () {
        return new Response<>(ResponseCode.SUCCESS, null);
    }

    public static <T> Response<T> from(T result) {
        return new Response<>(ResponseCode.SUCCESS, result);
    }

    public static Response<Void> error(ResponseCode responseCode) {
        return new Response<>(responseCode, null);
    }
}
