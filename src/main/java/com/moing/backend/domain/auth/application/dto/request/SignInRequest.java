package com.moing.backend.domain.auth.application.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignInRequest {
    @NotBlank(message="token 을 입력해주세요.")
    private String token;
}

