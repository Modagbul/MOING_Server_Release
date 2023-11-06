package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoMoreCreateMission extends MissionException {

    public NoMoreCreateMission() {
        super(ErrorCode.NO_MORE_CREATE_MISSION,
                HttpStatus.UNAUTHORIZED);
    }
}
