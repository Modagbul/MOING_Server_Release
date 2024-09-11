package com.moing.backend.domain.report.domain.repository;

import com.moing.backend.domain.report.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
