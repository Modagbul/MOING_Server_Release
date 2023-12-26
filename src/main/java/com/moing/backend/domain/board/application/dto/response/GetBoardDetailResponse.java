package com.moing.backend.domain.board.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class GetBoardDetailResponse {

    private Long boardId;

    private String writerNickName;

    private Boolean writerIsLeader;

    private String writerProfileImage;

    private String title;

    private String content;

    private String createdDate;

    private Boolean isWriter;

    private Boolean isNotice;

    private Long makerId;
}
