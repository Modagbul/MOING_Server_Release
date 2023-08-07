package com.moing.backend.domain.member.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundBySocialIdException extends MemberException {
    public NotFoundBySocialIdException() {
        super(ErrorCode.NOT_FOUND_BY_SOCIAL_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
