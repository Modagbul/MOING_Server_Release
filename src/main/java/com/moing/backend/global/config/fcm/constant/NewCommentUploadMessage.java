package com.moing.backend.global.config.fcm.constant;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NewCommentUploadMessage {

    NEW_COMMENT_UPLOAD_MESSAGE("%s", "[%s님의 댓글] %s");

    private final String title;
    private final String body;

    public String title(String commentContent) {
        return String.format(title, commentContent);
    }

    public String body(String writerNickname, String boardTitle) {
        return String.format(body, writerNickname, boardTitle);
    }
}
