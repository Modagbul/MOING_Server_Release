package com.moing.backend.domain.team.presentation;

import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.request.UpdateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.*;
import com.moing.backend.domain.team.application.service.*;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.team.presentation.constant.TeamResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
    private final CreateTeamUseCase createTeamService;
    private final GetTeamUseCase getTeamUseCase;
    private final SignInTeamUseCase signInTeamUseCase;
    private final DisbandTeamUseCase disbandTeamUseCase;
    private final WithdrawTeamUseCase withdrawTeamUseCase;
    private final UpdateTeamUseCase updateTeamUseCase;
    private final ReviewTeamUseCase reviewTeamUseCase;

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
     * 소모임 전체 조회하기 (소모임 홈화면) : 인증사진 제외
     * [GET] api/team
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetTeamResponse>> getTeam(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(GET_TEAM_SUCCESS.getMessage(), this.getTeamUseCase.getTeam(user.getSocialId())));
    }

    /**
     * 소모임 하나만 조회하기 (목표보드) : 소모임 레벨, 상태 바 제외
     * [GET] api/team/{teamId}
     * 작성자: 김민수
     */
    @GetMapping("/board/{teamId}")
    public ResponseEntity<SuccessResponse<GetTeamDetailResponse>> getTeamDetail(@AuthenticationPrincipal User user,
                                                                                @PathVariable Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_TEAM_DETAIL_SUCCESS.getMessage(), this.getTeamUseCase.getTeamDetailResponse(user.getSocialId(), teamId)));
    }

    /**
     * 소모임 가입하기 (소모임원으로 입장)
     * [POST] api/team/{teamId}
     * 작성자 : 김민수
     */

    @PostMapping("/{teamId}")
    public ResponseEntity<SuccessResponse<CreateTeamResponse>> signInTeam(@AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(SIGNIN_TEAM_SUCCESS.getMessage(),this.signInTeamUseCase.signInTeam(user.getSocialId(), teamId)));
    }

    /**
     * 소모임 강제 종료 (소모임장 권한)
     * [DELETE] api/team/{teamId}/disband
     * 작성자:김민수
     */
    @DeleteMapping("/{teamId}/disband")
    public ResponseEntity<SuccessResponse<DeleteTeamResponse>> disbandTeam(@AuthenticationPrincipal User user,
                                                                           @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(DISBAND_TEAM_SUCCESS.getMessage(), this.disbandTeamUseCase.disbandTeam(user.getSocialId(), teamId)));
    }

    /**
     * 소모임 탈퇴 (소모임원)
     * [DELETE] api/team/{teamId}/withdraw
     * 작성자: 김민수
     */
    @DeleteMapping("/{teamId}/withdraw")
    public ResponseEntity<SuccessResponse<DeleteTeamResponse>> withdrawTeam(@AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(WITHDRAW_TEAM_SUCCESS.getMessage(), this.withdrawTeamUseCase.withdrawTeam(user.getSocialId(),teamId)));
    }

    /**
     * 소모임 강제 종료, 탈퇴 전 정보 보여주기
     * [GET] api/team/{teamId}/review
     * 작성자: 김민수
     */
    @GetMapping("/{teamId}/review")
    public ResponseEntity<SuccessResponse<ReviewTeamResponse>> reviewTeam(@AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(REVIEW_TEAM_SUCCESS.getMessage(), this.reviewTeamUseCase.reviewTeam(user.getSocialId(), teamId)));
    }


    /**
     * 소모임 수정 (소모임장)
     * [POST] api/team/{teamId}
     */

    @PutMapping("/{teamId}")
    public ResponseEntity<SuccessResponse<UpdateTeamResponse>> updateTeam(@Valid @RequestBody UpdateTeamRequest updateTeamRequest,
                                                                          @AuthenticationPrincipal User user,
                                                                          @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_TEAM_SUCCESS.getMessage(), this.updateTeamUseCase.updateTeam(updateTeamRequest, user.getSocialId(), teamId)));
    }

    /**
     * 소모임 수정 전 조회
     * [GET] api/team/{teamid}
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<SuccessResponse<GetCurrentStatusResponse>> getCurrentStatus(@AuthenticationPrincipal User user,
                                                                                      @PathVariable Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_CURRENT_STATUS_SUCCESS.getMessage(), this.getTeamUseCase.getCurrentStatus(teamId)));
    }

    /**
     * 소모임 닉네임과 소모임 개수 조회
     * [GET] api/team/{teamId}/count
     */
    @GetMapping("/{teamId}/count")
    public ResponseEntity<SuccessResponse<GetTeamCountResponse>> getTeamCount(@AuthenticationPrincipal User user,
                                                                              @PathVariable Long teamId){
        return ResponseEntity.ok(SuccessResponse.create(GET_TEAM_COUNT_SUCCESS.getMessage(), this.getTeamUseCase.getTeamCount(user.getSocialId(),teamId)));
    }

    @PostMapping(value = "/test", name = "테스트")
    public void test() {
        Thread thread1 = new Thread(() -> {
            this.signInTeamUseCase.signInTeam("KAKAO@tester01", 1L);
        });
        Thread thread2 = new Thread(() -> {
            this.signInTeamUseCase.signInTeam("KAKAO@tester01", 1L);
        });
        thread1.start();
        thread2.start();
    }

}
