package com.moing.backend.domain.report.presentation;

import com.moing.backend.domain.report.application.service.ReportCreateUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.moing.backend.domain.report.presentation.constant.ReportResponseMessage.CREATE_REPORT_SUCCESS;

@RestController
@RequiredArgsConstructor
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
