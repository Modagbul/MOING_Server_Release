package com.moing.backend.domain.mission.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MissionReadRes {

    private String title;
    private String dueTo;

    private String rule;
    private String content;

    private String type;
    private String way;

    private Boolean isLeader;

}
