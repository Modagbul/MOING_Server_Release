package com.moing.backend.domain.mission.domain.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionStatus {
    END,
    ONGOING,
    SUCCESS,
    FAIL
}