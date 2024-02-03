package com.moing.backend.domain.mission.application.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MissionConfirmRes {
    private Long missionId;
}
