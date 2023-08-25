package com.moing.backend.domain.team.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamResponseMessage {
    CREATE_TEAM_SUCCESS("소모임을 생성하였습니다");
    private final String message;
}
