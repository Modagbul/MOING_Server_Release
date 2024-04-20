package com.moing.backend.domain.statistics.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DAUStatusType {
    DAILY_TEAM_COUNT("[DAU] 일일 모임 생성 수"),
    DAILY_MEMBER_COUNT("[DAU] 일일 신규 가입자 수"),
    DAILY_REPEAT_MISSION_COUNT("[DAU] 일일 반복 미션 생성 개수"),
    DAILY_ONCE_MISSION_COUNT("[DAU] 일일 한번 미션 생성 개수"),
    DAILY_MISSION_ARCHIVE_COUNT("[DAU] 일일 미션 인증 개수"),
    DAILY_FIRE_COUNT("[DAU] 일일 불 던지기 생성 개수");
    private final String message;
}
