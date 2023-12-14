package com.example.blog.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsaDto {

    private String publicKeyStr;
    private String modulusStr;
    private String exponentStr;
}
