package com.moing.backend.domain.team.presentation;

import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.CreateTeamResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.application.service.CreateTeamUserCase;
import com.moing.backend.domain.team.application.service.GetTeamUserCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.team.presentation.constant.TeamResponseMessage.CREATE_TEAM_SUCCESS;
import static com.moing.backend.domain.team.presentation.constant.TeamResponseMessage.GET_TEAM_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class TeamController {
    private final CreateTeamUserCase createTeamService;
    private final GetTeamUserCase getTeamUserCase;

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


}
