package com.moing.backend.domain.missionComment.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundByMissionCommentIdException extends MissionCommentException {
    public NotFoundByMissionCommentIdException() {
        super(ErrorCode.NOT_FOUND_BY_MISSION_COMMENT_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
