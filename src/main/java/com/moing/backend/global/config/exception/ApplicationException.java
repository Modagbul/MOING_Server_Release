package com.moing.backend.global.config.exception;

import com.moing.backend.global.config.response.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    protected ApplicationException(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ErrorCode getErrorCode() { return errorCode;}

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
