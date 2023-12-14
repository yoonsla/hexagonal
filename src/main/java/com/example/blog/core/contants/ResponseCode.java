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
    NOT_SAME_PASSWORD(20001, HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    IS_EXPIRED_TOKEN(20002, HttpStatus.FORBIDDEN.value(), "토큰이 만료되었습니다."),
    IS_EXPIRED_PASSWORD_AUTH(20003, HttpStatus.FORBIDDEN.value(), "비밀번호 유효시간이 지났습니다."),
    INVALID_ACCESS_TOKEN(20004, HttpStatus.UNAUTHORIZED.value(), "토큰 정보가 유효하지 않습니다."),
    USER_ROLE_MISMATCH(20005, HttpStatus.UNAUTHORIZED.value(), "유저 권한이 일치하지 않습니다."),
    RSA_KEY_NOT_EXIST(30000, HttpStatus.BAD_REQUEST.value(),"RSA Key 가 존재하지 않습니다."),
    RSA_DECODE_FAIL(30001, HttpStatus.SERVICE_UNAVAILABLE.value(),"RSA 복호화 실패"),
    UNKNOWN_ERROR(-1, HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 에러 발생");
    private int code;
    private int httpStatusCode;
    private String message;
}
