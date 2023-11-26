package com.moing.backend.domain.team.presentation;

import com.moing.backend.domain.team.application.dto.response.GetNewTeamResponse;
import com.moing.backend.domain.team.application.service.ApproveTeamUseCase;
import com.moing.backend.domain.team.application.service.GetTeamUseCase;
import com.moing.backend.domain.team.application.service.RejectTeamUseCase;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moing.backend.domain.team.presentation.constant.TeamResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/team")
public class AdminTeamController {


    private final ApproveTeamUseCase approveTeamUseCase;
    private final RejectTeamUseCase rejectTeamUseCase;
    private final GetTeamUseCase getTeamUseCase;

    /**
     * 소모임 승인
     * [POST] api/admin/team/approval??teamIds=1,2,3
     * 작성자 : 김민수
     */

    @PostMapping("/approval")
    public ResponseEntity<SuccessResponse> sendApproveAlarm(@RequestParam List<Long> teamIds) {
        this.approveTeamUseCase.approveTeams(teamIds);
        return ResponseEntity.ok(SuccessResponse.create(SEND_APPROVAL_ALARM_SUCCESS.getMessage()));
    }

    /**
     * 소모임 반려
     * [POST] api/admin/team/rejection?teamIds=1,2,3
     * 작성자: 김민수
     */
    @PostMapping("/rejection")
    public ResponseEntity<SuccessResponse> sendRejectionAlarm(@RequestParam List<Long> teamIds) {
        this.rejectTeamUseCase.rejectTeams(teamIds);
        return ResponseEntity.ok(SuccessResponse.create(SEND_REJECTION_ALARM_SUCCESS.getMessage()));
    }

    /**
     * 소모임 전체 조회
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<Page<GetNewTeamResponse>>> getNewTeam(@RequestParam(value = "dateSort", defaultValue = "asc") String dateSort,
                                                                                Pageable pageable) {
        return ResponseEntity.ok(SuccessResponse.create(GET_NEW_TEAM_SUCCESS.getMessage(), this.getTeamUseCase.getNewTeam(dateSort, pageable)));
    }

}
