package com.moing.backend.domain.fire.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundFireException extends FireException {

    public NotFoundFireException() {
        super(ErrorCode.NOT_FOUND_FIRE,
                HttpStatus.NOT_FOUND);
    }
}
