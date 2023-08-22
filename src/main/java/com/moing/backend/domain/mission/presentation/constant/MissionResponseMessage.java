package com.moing.backend.domain.mission.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionResponseMessage {
    CREATE_MISSION_SUCCESS("미션 생성을 완료 했습니다"),
    SIGN_UP_SUCCESS("회원 가입을 했습니다"),
    REISSUE_TOKEN_SUCCESS("토큰을 재발급했습니다");
    private final String message;
}

