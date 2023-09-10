package com.moing.backend.domain.mission.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionResponseMessage {
    CREATE_MISSION_SUCCESS("미션 생성을 완료 했습니다"),
    READ_MISSION_SUCCESS("미션 조회를 완료 했습니다"),
    UPDATE_MISSION_SUCCESS("미션 수정을 완료 했습니다"),
    DELETE_MISSION_SUCCESS("미션 삭제를 완료 했습니다");

    private final String message;
}

