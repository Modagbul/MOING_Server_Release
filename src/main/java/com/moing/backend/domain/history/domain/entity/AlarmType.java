package com.moing.backend.domain.history.domain.entity;

import lombok.Getter;

@Getter
public enum AlarmType {
    NEW_UPLOAD,
    FIRE,
    REMIND,
    APPROVE_TEAM,
    REJECT_TEAM
}
