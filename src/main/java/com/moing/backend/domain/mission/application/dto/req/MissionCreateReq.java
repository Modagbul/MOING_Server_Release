package com.moing.backend.domain.mission.application.dto.req;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Builder
public class MissionCreateReq {

    private String title;
    private String dueTo;

    private String rule;
    private String content;
    private Long number;

    private String type;
    private String way;

}
