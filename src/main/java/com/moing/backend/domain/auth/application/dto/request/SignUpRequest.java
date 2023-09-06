package com.moing.backend.domain.auth.application.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpRequest {
    @NotBlank(message = "nickName 을 입력해주세요.")
    @Size(min = 1, max = 10, message="nickName 은 최소 1개, 최대 10개의 문자만 입력 가능합니다.")
    private String nickName;

    @NotBlank(message = "fcmToken 을 입력해주세요.")
    private String fcmToken;
}
