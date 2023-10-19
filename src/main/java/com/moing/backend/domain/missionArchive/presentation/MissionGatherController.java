package com.moing.backend.domain.missionArchive.presentation;

import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.service.MissionArchiveBoardUseCase;
import com.moing.backend.domain.mission.application.service.MissionGatherBoardUseCase;
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

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team")
public class MissionGatherController {

    private final MissionGatherBoardUseCase missionGatherBoardUseCase;


    /**
     * 미션 모아보기 - 단일 미션
     * [GET] my-single
     * 작성자 : 정승연
     */

    @GetMapping("/my-single")
    public ResponseEntity<SuccessResponse<List<GatherSingleMissionRes>>> getMyActiveSingleMission(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_SINGLE_MISSION_SUCCESS.getMessage(), this.missionGatherBoardUseCase.getAllActiveSingleMissions(user.getSocialId())));
    }

    /**
     * 미션 모아보기 - 반복 미션
     * [GET] my-repeat
     * 작성자 : 정승연
     */

    @GetMapping("/my-repeat")
    public ResponseEntity<SuccessResponse<List<RepeatMissionBoardRes>>> getMyActiveRepeatMission(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_REPEAT_MISSION_SUCCESS.getMessage(), this.missionGatherBoardUseCase.getActiveRepeatMissions(teamId, user.getSocialId())));
    }

    /**
     * 모임별 미션 - 단일 미션
     * [GET] {teamId}/missions/board/repeat
     * 작성자 : 정승연
     */
    @GetMapping("/{teamId}/mission/single")
    public ResponseEntity<SuccessResponse<List<RepeatMissionBoardRes>>> geActiveRepeatMissionByTeam(@AuthenticationPrincipal User user,
                                                                                               @PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_REPEAT_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getActiveRepeatMissions(teamId, user.getSocialId())));
    }
    /**
     * 모임별 미션 - 반복 미션
     * [GET] {teamId}/missions/board/repeat
     * 작성자 : 정승연
     */
    @GetMapping("/{teamId}/mission/repeat")
    public ResponseEntity<SuccessResponse<List<RepeatMissionBoardRes>>> getActiveRepeatMissionByTeam(@AuthenticationPrincipal User user,
                                                                                               @PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_REPEAT_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getActiveRepeatMissions(teamId, user.getSocialId())));
    }



}
