package com.moing.backend.domain.report.domain.service;

import com.moing.backend.domain.report.domain.entity.Report;
import com.moing.backend.domain.report.domain.repository.ReportRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ReportSaveService {

    private final ReportRepository reportRepository;

    public Report save(Report report) {
        return reportRepository.save(report);
    }
}
