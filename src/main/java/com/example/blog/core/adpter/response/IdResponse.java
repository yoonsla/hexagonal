package com.example.blog.core.adpter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdResponse {

    private Long id;

    public static IdResponse from(Long id) {
        return IdResponse.builder()
            .id(id)
            .build();
    }
}
