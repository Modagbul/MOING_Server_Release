package com.moing.backend.domain.history.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundAlarmHistoryException extends AlarmHistoryException {
    public NotFoundAlarmHistoryException() {
        super(ErrorCode.NOT_FOUND_BY_ALARM_HISOTRY_ID_ERROR,
                HttpStatus.NOT_FOUND);
    }
}
