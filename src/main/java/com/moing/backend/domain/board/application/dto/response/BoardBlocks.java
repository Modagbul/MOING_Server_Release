package com.moing.backend.domain.board.application.dto.response;

import lombok.Getter;

@Getter
public class BoardBlocks {

    private Long boardId;

    private String writerNickName;

    private Boolean isLeader;

    private String writerProfileImage;

    private String title;

    private String content;

    private Integer commentNum;

    private Boolean isRead;
}
