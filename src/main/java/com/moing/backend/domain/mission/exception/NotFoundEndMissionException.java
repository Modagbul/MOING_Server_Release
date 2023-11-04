package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundEndMissionException extends MissionException {

    public NotFoundEndMissionException() {
        super(ErrorCode.NOT_FOUND_MISSION,
                HttpStatus.NOT_FOUND);
    }
}
