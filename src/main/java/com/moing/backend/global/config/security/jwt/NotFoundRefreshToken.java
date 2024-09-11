package com.moing.backend.global.config.security.jwt;

import com.moing.backend.global.exception.ApplicationException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundRefreshToken extends ApplicationException {
    public NotFoundRefreshToken() {
        super(ErrorCode.NOT_FOUND_REFRESH_TOKEN_ERROR,
                HttpStatus.UNAUTHORIZED);
    }

}
