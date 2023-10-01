package com.moing.backend.domain.mission.presentation;

import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionDeleteUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.domain.mission.application.service.MissionUpdateUseCase;
import com.moing.backend.domain.mission.domain.service.MissionDeleteService;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/missions")
public class MissionController {


    private final MissionCreateUseCase missionCreateUseCase;
    private final MissionReadUseCase missionReadUseCase;
    private final MissionUpdateUseCase missionUpdateUseCase;
    private final MissionDeleteUseCase missionDeleteUseCase;
    private final MissionQueryService missionQueryService;

    /**
     * 미션 조회
     * [GET] {teamId}/mission/{missionId}
     * 작성자 : 정승연
     */

    @GetMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<MissionReadRes>> getMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId, @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_MISSION_SUCCESS.getMessage(), this.missionReadUseCase.getMission(user.getSocialId(),missionId)));
    }



    /**
     * 미션 생성
     * [POST] {teamId}/mission
     * 작성자 : 정승연
     */

    @PostMapping()
    public ResponseEntity<SuccessResponse<MissionCreateRes>> createMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId, @RequestBody MissionReq missionReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_MISSION_SUCCESS.getMessage(), this.missionCreateUseCase.createMission(user.getSocialId(),teamId,missionReq)));
    }

    /**
     * 미션 수정
     * [PUT] {teamId}/mission/{missionId}
     * 작성자 : 정승연
     */
    @PutMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<MissionCreateRes>> updateMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId, @PathVariable("missionId") Long missionId, @RequestBody MissionReq missionReq) {
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_MISSION_SUCCESS.getMessage(), this.missionUpdateUseCase.updateMission(user.getSocialId(),missionId, missionReq)));
    }

    /**
     * 미션 삭제
     * [DELETE] {teamId}/mission/{missionId}
     * 작성자 : 정승연
     */
    @DeleteMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<Long>> deleteMission(@AuthenticationPrincipal User user,@PathVariable Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(DELETE_MISSION_SUCCESS.getMessage(), this.missionDeleteUseCase.deleteMission(user.getSocialId(),missionId)));
    }


}
