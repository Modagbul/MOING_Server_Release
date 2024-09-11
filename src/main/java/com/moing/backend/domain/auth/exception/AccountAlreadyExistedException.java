package com.moing.backend.domain.auth.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class AccountAlreadyExistedException extends AuthException {
    public AccountAlreadyExistedException() {
        super(ErrorCode.ACCOUNT_ALREADY_EXIST,
                HttpStatus.UNAUTHORIZED);
    }
}

