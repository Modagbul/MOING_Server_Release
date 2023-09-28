package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.exception.ApplicationException;
import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundMissionException extends MissionException {

    public NotFoundMissionException() {
        super(ErrorCode.NOT_FOUND_MISSION,
                HttpStatus.NOT_FOUND);
    }
}
