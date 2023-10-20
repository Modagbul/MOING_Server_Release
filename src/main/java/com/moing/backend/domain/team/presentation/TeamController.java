package com.moing.backend.domain.team.presentation;

import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.request.UpdateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.CreateTeamResponse;
import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.application.dto.response.UpdateTeamResponse;
import com.moing.backend.domain.team.application.service.*;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.team.presentation.constant.TeamResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
    private final CreateTeamUserCase createTeamService;
    private final GetTeamUserCase getTeamUserCase;
    private final SignInTeamUserCase signInTeamUserCase;
    private final DisbandTeamUserCase disbandTeamUserCase;
    private final WithdrawTeamUserCase withdrawTeamUserCase;
    private final UpdateTeamUserCase updateTeamUserCase;

    /**
     * 소모임 생성 (only 개설만)
     * [POST] api/team
     * 작성자 : 김민수
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateTeamResponse>> createTeam(@AuthenticationPrincipal User user,
                                                                          @Valid @RequestBody CreateTeamRequest createTeamRequest) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_TEAM_SUCCESS.getMessage(), this.createTeamService.createTeam(createTeamRequest,user.getSocialId())));
    }

    /**
     * 소모임 조회하기 (소모임 홈화면) : 인증사진 제외
     * [GET] api/team
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetTeamResponse>> getTeam(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(GET_TEAM_SUCCESS.getMessage(), this.getTeamUserCase.getTeam(user.getSocialId())));
    }

    /**
     * 소모임 가입하기 (소모임원으로 입장)
     * [POST] api/team/{teamId}
     * 작성자 : 김민수
     */

    @PostMapping("/{teamId}")
    public ResponseEntity<SuccessResponse<CreateTeamResponse>> signInTeam(@AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(SIGNIN_TEAM_SUCCESS.getMessage(),this.signInTeamUserCase.signInTeam(user.getSocialId(), teamId)));
    }

    /**
     * 소모임 강제 종료 (소모임장 권한)
     * [DELETE] api/team/{teamId}/disband
     * 작성자:김민수
     */
    @DeleteMapping("/{teamId}/disband")
    public ResponseEntity<SuccessResponse<DeleteTeamResponse>> disbandTeam(@AuthenticationPrincipal User user,
                                                                           @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(DISBAND_TEAM_SUCCESS.getMessage(), this.disbandTeamUserCase.disbandTeam(user.getSocialId(), teamId)));
    }

    /**
     * 소모임 탈퇴 (소모임원)
     * [DELETE] api/team/{teamId}/withdraw
     * 작성자: 김민수
     */
    @DeleteMapping("/{teamId}/withdraw")
    public ResponseEntity<SuccessResponse<DeleteTeamResponse>> withdrawTeam(@AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(WITHDRAW_TEAM_SUCCESS.getMessage(), this.withdrawTeamUserCase.withdrawTeam(user.getSocialId(),teamId)));
    }

    /**
     * 소모임 수정 (소모임장)
     * [POST] api/team/{teamId}
     */

    @PutMapping("/{teamId}")
    public ResponseEntity<SuccessResponse<UpdateTeamResponse>> updateTeam(@Valid @RequestBody UpdateTeamRequest updateTeamRequest,
                                                                          @AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_TEAM_SUCCESS.getMessage(), this.updateTeamUserCase.updateTeam(updateTeamRequest, user.getSocialId(), teamId)));
    }

}
