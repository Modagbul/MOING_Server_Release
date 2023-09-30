package com.moing.backend.domain.boardComment.presentattion.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardCommentResponseMessage {
    CREATE_BOARD_COMMENT_SUCCESS("댓글을 생성했습니다."),
    GET_BOARD_COMMENT_ALL_SUCCESS("댓글 목록을 모두 조회했습니다."),
    DELETE_BOARD_COMMENT_SUCCESS("댓글을 삭제했습니다");
    private final String message;
}