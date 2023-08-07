package com.moing.backend.global.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    protected ApplicationException(ErrorCode errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ErrorCode getErrorCode() { return errorCode;}

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
