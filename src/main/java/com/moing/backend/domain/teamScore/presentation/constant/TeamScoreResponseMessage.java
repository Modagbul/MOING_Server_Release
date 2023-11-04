package com.moing.backend.domain.teamScore.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamScoreResponseMessage {
    GET_TEAMSCORE_SUCCESS("팀 경험치/레벨 조회를 완료 했습니다");

    private final String message;
}

