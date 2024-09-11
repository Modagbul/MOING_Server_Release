package com.moing.backend.domain.block.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlockResponseMessage {

    CREATE_BLOCK_SUCCESS("사용자 차단을 완료했습니다."),
    GET_BLOCK_SUCCESS("사용자 차단 목록 조회를 완료했습니다."),
    DELETE_BLOCK_SUCCESS("사용자 차단 해제를 완료했습니다.");

    private final String message;

}
