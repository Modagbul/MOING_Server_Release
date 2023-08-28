package com.moing.backend.domain.mission.application.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionReq {

    private String title;
    private String dueTo;

    private String rule;
    private String content;
    private int number;

    private String type;
    private String way;
    private String status;

}
