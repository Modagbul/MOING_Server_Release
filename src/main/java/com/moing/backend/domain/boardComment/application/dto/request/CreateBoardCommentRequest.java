package com.moing.backend.domain.boardComment.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class CreateBoardCommentRequest {
    @NotBlank(message = "content 을 입력해 주세요.")
    private String content;
}

