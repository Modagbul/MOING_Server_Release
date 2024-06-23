package com.moing.backend.domain.fire.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class FireThrowReq {

    @Nullable
    private String message;
}
