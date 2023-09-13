package com.moing.backend.domain.team.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamResponseMessage {
    CREATE_TEAM_SUCCESS("소모임을 생성하였습니다"),
    GET_TEAM_SUCCESS("홈 화면에서 내 소모임을 모두 조회했습니다."),
    DISBAND_TEAM_SUCCESS("[소모임장 권한] 소모임을 강제 종료했습니다.");
    private final String message;
}
