package com.moing.backend.domain.mission.domain.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionStatus {
    WAIT,
    DONE,
    ONGOING,
    SKIP;

}