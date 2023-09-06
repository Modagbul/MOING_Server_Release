package com.moing.backend.domain.mission.application.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionReadRes {

    private String title;
    private String dueTo;

    private String rule;
    private String content;

    private String type;
    private String way;

}
