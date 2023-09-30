package com.moing.backend.domain.board.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardResponseMessage {
    CREATE_BOARD_SUCCESS("게시글을 생성했습니다."),
    GET_BOARD_ALL_SUCCESS("게시글 목록을 모두 조회했습니다."),
    UPDATE_BOARD_SUCCESS("게시글을 수정했습니다."),
    GET_BOARD_DETAIL_SUCCESS("게시글 상세 조회했습니다."),
    DELETE_BOARD_SUCCESS("게시글을 삭제했습니다");
    private final String message;
}
