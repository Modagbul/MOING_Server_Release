package com.moing.backend.domain.boardComment.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private Boolean writerIsDeleted;

    private String createdDate;

    private Long makerId;

    @QueryProjection
    public CommentBlocks(Long boardCommentId, String content, String writerNickName, Boolean writerIsLeader, String writerProfileImage, Boolean isWriter, Boolean writerIsDeleted, LocalDateTime createdDate, Long makerId) {
        this.boardCommentId = boardCommentId;
        this.writerNickName = writerNickName;
        this.writerIsLeader = writerIsLeader;
        this.writerProfileImage = writerProfileImage;
        this.content = content;
        this.isWriter = isWriter;
        this.writerIsDeleted=writerIsDeleted;
        this.createdDate = getFormattedDate(createdDate);
        this.makerId = makerId;
        deleteMember();
    }

    public void deleteMember() {
        if (Boolean.TRUE.equals(writerIsDeleted)) {
            this.writerNickName = "(알 수 없음)";
            this.writerProfileImage = null;
        }
    }

    public String getFormattedDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return localDateTime.format(formatter);
    }
}
