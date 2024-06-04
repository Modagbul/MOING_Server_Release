package com.moing.backend.domain.report.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StrategyCategory {

    BCOMMENT("boardCommentReportStrategy"),
    BOARD("boardReportStrategy"),
    MISSION("missionArchiveReportStrategy"),
    MCOMMENT("missionCommentReportStrategy");

    private final String strategyName;
}
