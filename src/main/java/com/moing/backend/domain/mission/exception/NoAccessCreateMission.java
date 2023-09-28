package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAccessCreateMission extends MissionException {

    public NoAccessCreateMission() {
        super(ErrorCode.NO_ACCESS_CREATE_MISSION,
                HttpStatus.UNAUTHORIZED);
    }
}
