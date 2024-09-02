package com.moing.backend.domain.report.presentation.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportResponseMessage {

    CREATE_REPORT_SUCCESS("게시글 신고를 완료 했습니다."),
    REPORT_MESSAGE("신고 접수로 삭제되었습니다."),
    REPORT_PHOTO("https://mo-ing.s3.ap-northeast-2.amazonaws.com/reportImage.png");

    private final String message;


}

