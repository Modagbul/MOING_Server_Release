package com.moing.backend.domain.missionArchive.application.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionArchiveCreateMessage {

    CREATOR_CREATE_MISSION_ARCHIVE("%s님이 미션을 인증했어요!"),
    TEAM_AND_TITLE("[%s] %s");

    private final String message;

    public String to(String creator) {
        return String.format(message, creator);
    }

    public String teamAndTitle(String team, String title) {
        return String.format(message, team, title);
    }
}
