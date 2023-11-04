package com.moing.backend.domain.fire.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundFireReceiversException extends FireException {

    public NotFoundFireReceiversException() {
        super(ErrorCode.NOT_FOUND_FIRE_RECEIVERS,
                HttpStatus.NOT_FOUND);
    }
}
