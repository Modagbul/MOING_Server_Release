package com.moing.backend.domain.auth.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class TokenInvalidException extends AuthException {
    public TokenInvalidException() {
        super(ErrorCode.TOKEN_INVALID_ERROR,
                HttpStatus.CONFLICT);
    }
}
