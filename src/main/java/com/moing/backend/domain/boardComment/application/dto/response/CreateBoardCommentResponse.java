package com.moing.backend.domain.boardComment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class CreateBoardCommentResponse {
    private Long boardCommentId;
}
