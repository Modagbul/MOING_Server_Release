package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundOngoingMissionException extends MissionException {

    public NotFoundOngoingMissionException() {
        super(ErrorCode.NOT_FOUND_MISSION,
                HttpStatus.NOT_FOUND);
    }
}
