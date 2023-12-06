package com.moing.backend.domain.report.presentation;

import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.report.application.service.ReportCreateUseCase;
import com.moing.backend.domain.report.domain.entity.Report;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.CREATE_ARCHIVE_SUCCESS;
import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.CREATE_REPORT_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    private final ReportCreateUseCase reportCreateUseCase;

    @PostMapping("/{reportType}/{targetId}")
    public ResponseEntity<SuccessResponse<Long>> createReport(@AuthenticationPrincipal User user,
                                                                            @PathVariable("reportType") String reportType,
                                                                            @PathVariable("targetId") Long targetId) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_REPORT_SUCCESS.getMessage(), this.reportCreateUseCase.createReport(user.getSocialId(), targetId,reportType)));
    }
}
