package com.moing.backend.domain.missionComment.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotAuthByMissionCommentException extends MissionCommentException {
    public NotAuthByMissionCommentException() {
        super(ErrorCode.NOT_AUTH_BY_MISSION_COMMENT_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
