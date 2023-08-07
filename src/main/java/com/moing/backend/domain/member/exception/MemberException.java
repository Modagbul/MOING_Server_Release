package com.moing.backend.domain.member.exception;

import com.moing.backend.global.exception.ApplicationException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class MemberException extends ApplicationException {
    protected MemberException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
