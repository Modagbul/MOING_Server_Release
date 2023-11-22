package com.moing.backend.global.config.fcm.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApproveTeamMessage {

    APPROVE_TEAM_MESSAGE("%s님, [%s] 모임이 타오를 준비를 마쳤어요!", "지금 바로 우리 모임원들을 초대해볼까요? 🔥"),

    REJECT_TEAM_MESSAGE("%s님, [%s] 모임 신청이 반려됐어요.", "신청서를 점검한 뒤 다시 한번 모임을 신청해주세요!");

    private final String title;
    private final String body;

    public String title(String memberName, String teamName) {
        return String.format(title, memberName, teamName);
    }

    public String body() {
        return body;
    }
}
