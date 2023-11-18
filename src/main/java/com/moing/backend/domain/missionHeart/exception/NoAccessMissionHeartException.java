package com.moing.backend.domain.missionHeart.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NoAccessMissionHeartException extends MissionHeartException {
    public NoAccessMissionHeartException() {
        super(ErrorCode.NO_ACCESS_HEART_FOR_ME, HttpStatus.NOT_FOUND);

    }
}
