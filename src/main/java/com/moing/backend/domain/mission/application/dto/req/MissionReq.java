package com.moing.backend.domain.mission.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionReq {

    private String title;
    private String dueTo;

    private String rule;
    private String content;
    private int number;

    private String type;
    private String way;

}
