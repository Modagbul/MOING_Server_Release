package com.moing.backend.domain.mission.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class GatherSingleMissionRes {
    private Long missionId;
    private Long teamId;
    private String teamName;
    private String missionTitle;
    private String dueTo;
    private String status;
    private Long done;
    private Long total;

    public GatherSingleMissionRes(Long missionId, Long teamId, String teamName, String missionTitle, String dueTo, String status, Long total) {
        this.missionId = missionId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.missionTitle = missionTitle;
        this.dueTo = dueTo;
        this.status = status;
        this.total = total;
    }
}
