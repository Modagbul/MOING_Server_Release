package com.moing.backend.domain.fire.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@NoArgsConstructor
@Getter
public class FireThrowReq {

    private String message;

    @Builder
    public FireThrowReq(String message) {
        this.message = message;
    }
}
