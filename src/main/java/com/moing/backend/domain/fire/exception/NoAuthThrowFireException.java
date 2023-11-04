package com.moing.backend.domain.fire.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAuthThrowFireException extends FireException {

    public NoAuthThrowFireException() {
        super(ErrorCode.NOT_AUTH_FIRE_THROW,
                HttpStatus.NOT_FOUND);
    }
}
