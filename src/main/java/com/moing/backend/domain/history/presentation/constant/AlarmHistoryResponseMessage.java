package com.moing.backend.domain.history.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmHistoryResponseMessage {
    GET_ALL_ALARM_HISTORY("알림 히스토리를 모두 조회했습니다."),
    READ_ALARM_HISTORY("알림 히스토리 한 개를 조회했습니다."),
    GET_UNREAD_ALARM_HISTORY("안읽은 알림 개수를 조회했습니다");
    private final String message;
}
