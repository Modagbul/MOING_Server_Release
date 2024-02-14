package com.moing.backend.domain.teamScore.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScoreStatus {
    PLUS(1L), MINUS(-1L);
    private final Long value;
}
