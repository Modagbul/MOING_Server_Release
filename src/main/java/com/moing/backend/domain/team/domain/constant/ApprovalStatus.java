package com.moing.backend.domain.team.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum ApprovalStatus {
    NO_CONFIRMATION("확인안함"),
    APPROVAL("승인"),
    REJECTION("거절");
    private final String message;
}
