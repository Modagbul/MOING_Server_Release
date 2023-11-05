package com.moing.backend.domain.missionState.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundMissionStateException extends MissionStateException{
    public NotFoundMissionStateException() {
        super(ErrorCode.NOT_FOUND_END_MISSION, HttpStatus.NOT_FOUND);

    }
}
