package com.moing.backend.domain.missionComment.exception;

import com.moing.backend.global.exception.ApplicationException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class MissionCommentException extends ApplicationException {
    protected MissionCommentException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}