package com.moing.backend.domain.report.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportResponseMessage {

    CREATE_REPORT_SUCCESS("게시글 신고를 완료 했습니다.");

    private final String message;


}

