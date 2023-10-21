package com.moing.backend.domain.missionArchive.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundMissionArchiveException extends MissionArchiveException {

    public NotFoundMissionArchiveException() {
        super(ErrorCode.NOT_FOUND_MISSION_ARCHIVE,
                HttpStatus.NOT_FOUND);
    }
}
