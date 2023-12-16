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


    REMIND_MISSION_MESSAGE1("자기계발 미션 도전과 함께⚡\uFE0F "),
    REMIND_MISSION_MESSAGE2("미션 인증을 모임원들이 기다리고 있어요!"),
    REMIND_MISSION_MESSAGE3("미션 인증하고 이번주도 도전해요👊"),
    REMIND_MISSION_MESSAGE4("미션 도전하고 성취감 뿜뿜💪"),


    REMIND_ON_SUNDAY_TITLE("이번 한 주도 고생 많았어요\uD83D\uDE0A"),
    REMIND_ON_SUNDAY_MESSAGE("내일부터 미션을 다시 시작해봐요\uD83D\uDCAA"),


    REMIND_ON_MONDAY_TITLE("경쾌한 한 주의 시작이에요!"),
    REMIND_ON_MONDAY_MESSAGE("이번주에도 미션 도전을 응원해요\uD83D\uDE0A\n");


    private final String message;

    }
