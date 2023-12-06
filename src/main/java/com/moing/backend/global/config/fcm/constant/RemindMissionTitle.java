package com.moing.backend.global.config.fcm.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RemindMissionTitle {

    REMIND_MISSION_TITLE1("오늘도 좋은 하루 보내세요!"),
    REMIND_MISSION_TITLE2("혹시 ... 잊으신 건 아니죠?\uD83D\uDC40"),
    REMIND_MISSION_TITLE3("좋은 아침이에요! ☀\uFE0F"),
    REMIND_MISSION_TITLE4("오늘의 열정이 타오르불\uD83D\uDD25"),


    REMIND_MISSION_MESSAGE1(" 자기계발 미션 도전과 함께⚡\uFE0F "),
    REMIND_MISSION_MESSAGE2(" 미션 인증을 모임원들이 기다리고 있어요!"),
    REMIND_MISSION_MESSAGE3(" 미션 인증하고 이번주도 도전해요👊 "),
    REMIND_MISSION_MESSAGE4(" 미션 도전하고 성취감 뿜뿜💪 ");

    private final String message;

    }
