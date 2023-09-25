package com.moing.backend.domain.missionArchive.presentation;

import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.application.service.MissionArchiveBoardUseCase;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
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
@RequestMapping("/api/{teamId}/missions/{missionId}/board")
public class MissionBoardController {

    private final MissionArchiveBoardUseCase missionArchiveBoardUseCase;

    /**
     * 단일 인증 조회
     * [POST] {teamId}/missions/{missionId}/board
     * 작성자 : 정승연
     */

    @GetMapping("/single")
    public ResponseEntity<SuccessResponse<List<SingleMissionBoardRes>>> getActiveSingleMission(@AuthenticationPrincipal User user,
                                                                                      @PathVariable("teamId") Long teamId,
                                                                                      @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(ACTIVE_SINGLE_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getActiveSingleMissions(teamId, user.getSocialId())));
    }

    @GetMapping("/finish")
    public ResponseEntity<SuccessResponse<List<FinishMissionBoardRes>>> getFinishAllMission(@AuthenticationPrincipal User user,
                                                                                      @PathVariable("teamId") Long teamId,
                                                                                      @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(FINISH_ALL_MISSION_SUCCESS.getMessage(), this.missionArchiveBoardUseCase.getFinishMissions(teamId, user.getSocialId())));
    }
}
