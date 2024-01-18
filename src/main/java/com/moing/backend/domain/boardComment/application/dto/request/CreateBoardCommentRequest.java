package com.moing.backend.domain.boardComment.application.dto.request;

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
public class CreateBoardCommentRequest {

    @NotBlank(message = "content 을 입력해 주세요.")
    @Size(min = 0, max = 300, message = "댓글 글자수를 초과했습니다.")
    private String content;
}

