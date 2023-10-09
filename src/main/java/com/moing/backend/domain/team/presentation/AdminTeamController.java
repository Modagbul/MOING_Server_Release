package com.moing.backend.domain.team.presentation;

import com.moing.backend.domain.team.application.service.SendTeamAlarmUserCase;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.moing.backend.domain.team.presentation.constant.TeamResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/team")
public class AdminTeamController {


    private final SendTeamAlarmUserCase sendTeamAlarmUserCase;

    /**
     * 소모임 승인 알림 보내기
     * [POST] api/admin/team/approval/{teamId}
     * 작성자 : 김민수
     */

    @PostMapping("/approval/{teamId}")
    public ResponseEntity<SuccessResponse> sendApproveAlarm(@PathVariable Long teamId) {
        this.sendTeamAlarmUserCase.sendApprovalAlarm(teamId);
        return ResponseEntity.ok(SuccessResponse.create(SEND_APPROVAL_ALARM_SUCCESS.getMessage()));
    }

    /**
     * 소모임 반려 알림 보내기
     * [POST] api/admin/team/rejection/{teamId}
     * 작성자: 김민수
     */
    @PostMapping("/rejection/{teamId}")
    public ResponseEntity<SuccessResponse> sendRejectionAlarm(@PathVariable Long teamId){
        this.sendTeamAlarmUserCase.sendRejectionAlarm(teamId);
        return ResponseEntity.ok(SuccessResponse.create(SEND_REJECTION_ALARM_SUCCESS.getMessage()));
    }

}
