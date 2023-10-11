package com.moing.backend.domain.board.application.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
public class UpdateBoardRequest {
    @NotBlank(message = "title 을 입력해 주세요.")
    @Size(min = 1, max = 15, message = "title 은 최소 1개, 최대 15개의 문자만 입력 가능합니다.")
    private String title;

    @NotBlank(message = "content 을 입력해 주세요.")
    @Size(min = 1, max = 300, message = "content 은 최소 1개, 최대 10개의 문자만 입력 가능합니다.")
    private String content;

    @NotNull(message = "notice 사용 여부(isNotice) 를 입력해 주세요.")
    private Boolean isNotice;
}
