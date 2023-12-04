package com.moing.backend.domain.report.application.mapper;

import com.moing.backend.domain.report.domain.entity.Report;
import com.moing.backend.domain.report.domain.entity.constant.ReportType;
import com.moing.backend.global.annotation.Mapper;

@Mapper
public class ReportMapper {

    public static Report mapToReport(Long memberId, Long targetId, String reportType) {
        return Report.builder()
                .reportMemberId(memberId)
                .reportType(ReportType.valueOf(reportType))
                .targetId(targetId)
                .build();
    }

}
