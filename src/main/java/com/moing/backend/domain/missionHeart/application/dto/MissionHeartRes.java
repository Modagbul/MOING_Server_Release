package com.moing.backend.domain.missionHeart.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class MissionHeartRes {
    private Long missionArchiveId;
    private String missionHeartStatus;
    private int hearts;
}
