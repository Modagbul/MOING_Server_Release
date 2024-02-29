package com.moing.backend.domain.mission.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionResponseMessage {
    CREATE_MISSION_SUCCESS("미션 생성을 완료 했습니다"),
    READ_MISSION_SUCCESS("미션 조회를 완료 했습니다"),
    UPDATE_MISSION_SUCCESS("미션 수정을 완료 했습니다"),
    END_MISSION_SUCCESS("미션 종료를 완료 했습니다"),
    DELETE_MISSION_SUCCESS("미션 삭제를 완료 했습니다"),
    RECOMMEND_MISSION_SUCCESS("미션 추천을 위한 팀 카테고리를 조회 했습니다"),
    CONFIRM_MISSION_SUCCESS("미션 설명을 확인했습니다.");

    private final String message;
}

