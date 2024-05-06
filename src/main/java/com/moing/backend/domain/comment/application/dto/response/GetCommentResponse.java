package com.moing.backend.domain.comment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class GetCommentResponse {
    private List<CommentBlocks> commentBlocks;
}
