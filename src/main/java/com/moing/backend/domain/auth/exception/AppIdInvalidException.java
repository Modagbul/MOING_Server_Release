package com.moing.backend.domain.auth.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class AppIdInvalidException extends AuthException{
    public AppIdInvalidException() {
        super(ErrorCode.APPID_INVALID_ERROR,
                HttpStatus.CONFLICT);
    }
}
