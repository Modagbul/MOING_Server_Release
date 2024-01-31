package com.moing.backend.domain.auth.application.dto.request;

import com.moing.backend.domain.member.domain.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Gender gender;

    private String birthDate;
}
