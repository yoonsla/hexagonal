package com.example.blog.core.contants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(0, HttpStatus.OK.value(), "성공"),
    IS_NOT_EXIST(10000, HttpStatus.BAD_REQUEST.value(), "존재하지 않습니다."),
    IS_NOT_WRITER(20000, HttpStatus.BAD_REQUEST.value(), "작성자가 다릅니다."),
    UNKNOWN_ERROR(-1, HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 에러 발생");

    private int code;
    private int httpStatusCode;
    private String message;
}
