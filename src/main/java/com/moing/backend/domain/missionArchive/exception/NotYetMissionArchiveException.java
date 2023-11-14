package com.moing.backend.domain.missionArchive.exception;

import com.moing.backend.global.response.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotYetMissionArchiveException extends MissionArchiveException {

    public NotYetMissionArchiveException() {
        super(ErrorCode.NOT_YET_MISSION_ARCHIVE,
                HttpStatus.NOT_FOUND);
    }
}
