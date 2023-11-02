package com.moing.backend.domain.teamScore.presentation;

import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.service.MissionGatherBoardUseCase;
import com.moing.backend.domain.teamScore.application.dto.TeamScoreRes;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.ACTIVE_REPEAT_MISSION_SUCCESS;
import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.ACTIVE_SINGLE_MISSION_SUCCESS;
import static com.moing.backend.domain.teamScore.presentation.constant.TeamScoreResponseMessage.GET_TEAMSCORE_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team/{teamId}")
public class TeamScoreController {

    private final TeamScoreLogicUseCase teamScoreLogicUseCase;

    /**
     * 팀별 불 레벨/경험치 조회
     * [GET] my-repeat
     * 작성자 : 정승연
     */

    @GetMapping("/my-fire")
    public ResponseEntity<SuccessResponse<TeamScoreRes>> getTeamScore(@PathVariable("teamId") Long teamId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(GET_TEAMSCORE_SUCCESS.getMessage(), this.teamScoreLogicUseCase.getTeamScoreInfo(teamId)));
    }





}
