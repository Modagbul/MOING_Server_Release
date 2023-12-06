package com.moing.backend.domain.mission.application.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionCreateRes {

    private Long missionId;

    private String title;
    private String dueTo;

    private String rule;
    private String content;
    private int number;

    private String type;
    private String status;
    private String way;

    private Boolean isLeader;

}
