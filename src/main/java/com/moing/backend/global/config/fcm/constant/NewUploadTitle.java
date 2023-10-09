package com.moing.backend.global.config.fcm.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NewUploadTitle{
    UPLOAD_NOTICE_NEW_TITLE("새로운 공지 알려드려요!"),
    UPLOAD_VOTE_NEW_TITLE("새로운 투표 알려드려요!"),
    UPLOAD_MISSION_NEW_TITLE("새로운 미션 알려드려요!");
    private final String title;
}
