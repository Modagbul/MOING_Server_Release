package com.moing.backend.domain.board.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateBoardResponse {
    private Long boardId;
}
