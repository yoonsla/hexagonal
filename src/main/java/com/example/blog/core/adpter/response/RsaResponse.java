package com.example.blog.core.adpter.response;

import com.example.blog.core.service.dto.RsaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RsaResponse {

    private String publicKeyStr;
    private String modulusStr;
    private String exponentStr;

    public static RsaResponse from(RsaDto rsaDto) {
        return new RsaResponse(
            rsaDto.getPublicKeyStr(),
            rsaDto.getModulusStr(),
            rsaDto.getExponentStr()
        );
    }
}
