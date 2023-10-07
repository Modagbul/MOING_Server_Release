package com.moing.backend.domain.missionArchive.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionArchiveResponseMessage {

    CREATE_ARCHIVE_SUCCESS("미션 인증을 완료 했습니다."),
    READ_MY_ARCHIVE_SUCCESS("나의 미션 인증 현황 조회를 완료 했습니다."),
    READ_TEAM_ARCHIVE_SUCCESS("팀원 미션 인증 현황 조회를 완료 했습니다."),
    HEART_UPDATE_SUCCESS("미션 인증 좋아요를 성공적으로 눌렀습니다."),
    MISSION_ARCHIVE_PEOPLE_STATUS_SUCCESS("미션 인증 성공한 인원 상태 조회를 완료 했습니다."),
    ACTIVE_SINGLE_MISSION_SUCCESS("진행 중인 한번 인증 미션 조회를 완료 했습니다."),
    ACTIVE_REPEAT_MISSION_SUCCESS("진행 중인 반복 인증 미션 조회를 완료 했습니다."),
    FINISH_ALL_MISSION_SUCCESS("종료된 미션 조회를 완료하였습니다.");

    private final String message;

}
