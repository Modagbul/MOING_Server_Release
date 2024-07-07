package com.moing.backend.domain.fire.application.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FireThrowReq {

    private String message;

    @Builder
    public FireThrowReq(String message) {
        this.message = message;
    }
}
