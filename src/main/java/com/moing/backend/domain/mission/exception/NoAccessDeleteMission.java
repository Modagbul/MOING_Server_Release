package com.moing.backend.domain.mission.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAccessDeleteMission extends MissionException {

    public NoAccessDeleteMission() {
        super(ErrorCode.NO_ACCESS_DELETE_MISSION,
                HttpStatus.UNAUTHORIZED);
    }
}
