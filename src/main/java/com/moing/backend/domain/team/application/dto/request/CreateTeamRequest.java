package com.moing.backend.domain.team.application.dto.request;

import com.moing.backend.domain.team.domain.constant.Category;
import com.moing.backend.global.annotation.Enum;
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
public class CreateTeamRequest {
    @NotBlank(message = "category 를 입력해 주세요.")
    @Enum(enumClass = Category.class)
    private String category;

    @NotBlank(message = "name 을 입력해 주세요.")
    @Size(min = 1, max = 10, message = "name 은 최소 1개, 최대 10개의 문자만 입력 가능합니다.")
    private String name;

    @NotBlank(message = "introduction 을 입력해 주세요.")
    @Size(min = 1, max = 300, message = "introduction 은 최소 1개, 최대 300개의 문자만 입력 가능합니다.")
    private String introduction;

    @NotBlank(message = "promise 를 입력해 주세요.")
    @Size(min = 1, max = 100, message = "promise 는 최소 1개, 최대 100개의 문자만 입력 가능합니다.")
    private String promise;

    @NotBlank(message = "profileImgUrl 을 입력해 주세요.")
    private String profileImgUrl;

}
