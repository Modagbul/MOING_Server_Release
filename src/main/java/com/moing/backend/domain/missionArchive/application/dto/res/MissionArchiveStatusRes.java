package com.moing.backend.domain.missionArchive.application.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionArchiveStatusRes {
    private String total;
    private String done;
}
