package com.moing.backend.domain.board.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardBlocks {

    private Long boardId;

    private String writerNickName;

    private Boolean writerIsLeader;

    private String writerProfileImage;

    private String title;

    private String content;

    private Integer commentNum;

    private Boolean isRead;

    @QueryProjection
    public BoardBlocks(Long boardId, String writerNickName, Boolean writerIsLeader, String writerProfileImage, String title, String content, Integer commentNum) {
        this.boardId = boardId;
        this.writerNickName = writerNickName;
        this.writerIsLeader = writerIsLeader;
        this.writerProfileImage = writerProfileImage;
        this.title = title;
        this.content = content;
        this.commentNum = commentNum;
        this.isRead = false;
    }

    public void readBoard() {
        this.isRead = true;
    }
}
