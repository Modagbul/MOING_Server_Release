package com.moing.backend.domain.board.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardBlocks {

    private Long boardId;

    private String writerNickName;

    private Boolean writerIsLeader;

    private String writerProfileImage;

    private String title;

    private String content;

    private Integer commentNum;

    private Boolean isRead;

    private Boolean writerIsDeleted;

    private boolean isNotice;

    private Long makerId;


    @QueryProjection
    public BoardBlocks(Long boardId, String writerNickName, Boolean writerIsLeader, String writerProfileImage, String title, String content, Integer commentNum, Boolean isRead, Boolean writerIsDeleted, boolean isNotice, Long makerId) {
        this.boardId = boardId;
        this.writerNickName = writerNickName;
        this.writerIsLeader = writerIsLeader;
        this.writerProfileImage = writerProfileImage;
        this.title = title;
        this.content = content;
        this.commentNum = commentNum;
        this.isRead = isRead;
        this.writerIsDeleted=writerIsDeleted;
        this.isNotice=isNotice;
        this.makerId = makerId;
        deleteMember();
    }

    public void readBoard() {
        this.isRead = true;
    }

    public boolean isNotice() {
        return isNotice;
    }

    public void deleteMember() {
        if(Boolean.TRUE.equals(writerIsDeleted)) {
            this.writerNickName = "(알 수 없음)";
            this.writerProfileImage = null;
        }
    }
}
