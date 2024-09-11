package com.moing.backend.domain.mypage.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class AlarmInvalidException extends MyPageException {
    public AlarmInvalidException() {
        super(ErrorCode.INVALID_ALARM_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
