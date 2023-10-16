package com.moing.backend.domain.missionArchive.presentation;

import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveBoardUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/missions/board")
public class MissionBoardController {

    private final MissionArchiveBoardUseCase missionArchiveBoardUseCase;

    /**
     * 단일 인증 조회
     * [GET] {teamId}/missions/board/single
     * 작성자 : 정승연
     */

    @GetMapping("/single")
    public ResponseEntity<SuccessResponse<List<SingleMissionBoardRes>>> getActiveSingleMission(@AuthenticationPrincipal User user,
                                                                                      @PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_SINGLE_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getActiveSingleMissions(teamId, user.getSocialId())));
    }

    /**
     * 반복 미션 인증 조회
     * [GET] {teamId}/missions/board/repeat
     * 작성자 : 정승연
     */
    @GetMapping("/repeat")
    public ResponseEntity<SuccessResponse<List<RepeatMissionBoardRes>>> getActiveRepeatMission(@AuthenticationPrincipal User user,
                                                                                               @PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_REPEAT_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getActiveRepeatMissions(teamId, user.getSocialId())));
    }

    /**
     * 종료된 인증 조회
     * [POST] {teamId}/missions/board/finish
     * 작성자 : 정승연
     */

    @GetMapping("/finish")
    public ResponseEntity<SuccessResponse<List<FinishMissionBoardRes>>> getFinishAllMission(@AuthenticationPrincipal User user,
                                                                                            @PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(FINISH_ALL_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getFinishMissions(teamId, user.getSocialId())));
    }

}
