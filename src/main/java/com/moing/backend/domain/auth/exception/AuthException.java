package com.moing.backend.domain.auth.exception;

import com.moing.backend.global.exception.ApplicationException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class AuthException extends ApplicationException {
    protected AuthException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
