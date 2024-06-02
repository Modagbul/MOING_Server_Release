package com.moing.backend.domain.report.application.service;

import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.report.application.mapper.ReportMapper;
import com.moing.backend.domain.report.domain.entity.Report;
import com.moing.backend.domain.report.domain.service.ReportSaveService;
import com.moing.backend.domain.report.presentation.constant.StrategyCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportCreateUseCase {

    private final ReportSaveService reportSaveService;
    private final Map<String, ReportStrategy> strategyMap;
    private final MemberGetService memberGetService;

    public Long createReport(String socialId, Long targetId, String reportType) {
        ReportStrategy strategy = strategyMap.get(StrategyCategory.valueOf(reportType).getStrategyName());
        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();
        String targetMemberNickName= strategy.processReport(targetId);
        Report save = reportSaveService.save(ReportMapper.mapToReport(memberId, targetId, reportType, targetMemberNickName));
        return save.getTargetId();
    }
}
