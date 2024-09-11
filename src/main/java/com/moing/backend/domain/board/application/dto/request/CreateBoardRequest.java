package com.moing.backend.domain.board.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class CreateBoardRequest {
    @NotBlank(message = "title 을 입력해 주세요.")
    @Size(min = 0, max = 30, message = "제목 글자수를 초과했습니다.")
    private String title;

    @NotBlank(message = "content 을 입력해 주세요.")
    @Size(min = 0, max = 300, message = "내용 글자수를 초과했습니다.")
    private String content;

    @NotNull(message = "notice 사용 여부(isNotice) 를 입력해 주세요.")
    private Boolean isNotice;
}
