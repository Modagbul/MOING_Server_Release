package com.moing.backend.domain.member.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundRemindAlarmException extends MemberException {
    public NotFoundRemindAlarmException() {
        super(ErrorCode.NOT_FOUND_ALL_MEMBER,
                HttpStatus.NOT_FOUND);
    }
}
