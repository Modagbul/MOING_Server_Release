package com.moing.backend.domain.mission.application.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherSingleMissionRes {
    private Long missionId;
    private String teamName;
    private String missionTitle;
    private String dueTo;
}
