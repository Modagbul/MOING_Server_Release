package com.moing.backend.global.config.fcm.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NewNoticeUploadMessage {

    NEW_NOTICE_UPLOAD_MESSAGE("%s에 새로 올라온 공지를 확인하세요!", "%s");

    private final String title;
    private final String body;

    public String title(String teamName) {
        return String.format(title, teamName);
    }

    public String body(String noticeTitle) {
        return String.format(title, noticeTitle);
    }
}
