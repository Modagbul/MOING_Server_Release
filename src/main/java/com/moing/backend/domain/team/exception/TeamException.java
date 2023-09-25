package com.moing.backend.domain.team.exception;

import com.moing.backend.global.exception.ApplicationException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class TeamException extends ApplicationException {
    protected TeamException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}