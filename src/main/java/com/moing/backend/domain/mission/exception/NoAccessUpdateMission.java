package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAccessUpdateMission extends MissionException {

    public NoAccessUpdateMission() {
        super(ErrorCode.NO_ACCESS_UPDATE_MISSION,
                HttpStatus.UNAUTHORIZED);
    }
}
