package com.moing.backend.domain.missionArchive.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionArchiveResponseMessage {

    CREATE_ARCHIVE_SUCCESS("미션 인증을 완료 했습니다."),
    READ_MY_ARCHIVE_SUCCESS("나의 미션 인증 현황 조회를 완료 했습니다."),
    READ_TEAM_ARCHIVE_SUCCESS("팀원 미션 인증 현황 조회를 완료 했습니다."),
    HEART_UPDATE_SUCCESS("미션 인증 좋아요를 성공적으로 눌렀습니다.");

    private final String message;

}
