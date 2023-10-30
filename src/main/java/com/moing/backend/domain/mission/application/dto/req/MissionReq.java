package com.moing.backend.domain.mission.application.dto.req;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class MissionReq {

    private String title;
    private String dueTo;

    private String rule;
    private String content;
    private int number;

    private String type;
    private String way;

}
