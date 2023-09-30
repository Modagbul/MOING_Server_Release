package com.moing.backend.domain.boardComment.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentBlocks {

    private Long boardCommentId;

    private String content;

    private String writerNickName;

    private Boolean writerIsLeader;

    private String writerProfileImage;

    private Boolean isWriter;
}
