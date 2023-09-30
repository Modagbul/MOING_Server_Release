package com.moing.backend.domain.boardComment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class GetBoardCommentResponse {
    private List<CommentBlocks> commentBlocks;
}
