package com.moing.backend.domain.team.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class UpdateTeamRequest {
    @NotBlank(message = "name 을 입력해 주세요.")
    @Size(min = 1, max = 10, message = "name 은 최소 1개, 최대 10개의 문자만 입력 가능합니다.")
    private String name;

    @NotBlank(message = "introduction 을 입력해 주세요.")
    @Size(min = 1, max = 300, message = "introduction 은 최소 1개, 최대 300개의 문자만 입력 가능합니다.")
    private String introduction;

    @NotBlank(message = "profileImgUrl 을 입력해 주세요.")
    private String profileImgUrl;
}
