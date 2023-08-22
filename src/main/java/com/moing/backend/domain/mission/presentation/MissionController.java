package com.moing.backend.domain.mission.presentation;

import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionDeleteUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.domain.mission.application.service.MissionUpdateUseCase;
import com.moing.backend.domain.mission.domain.service.MissionDeleteService;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/mission")
public class MissionController {


    private final MissionCreateUseCase missionCreateUseCase;
    private final MissionReadUseCase missionReadUseCase;
    private final MissionUpdateUseCase missionUpdateUseCase;
    private final MissionDeleteUseCase missionDeleteUseCase;

    /**
     * 미션 조회
     * [GET] /mission/{missionId}
     * 작성자 : 정승연
     */

    @GetMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<MissionReadRes>> getMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_MISSION_SUCCESS.getMessage(), this.missionReadUseCase.getMission(missionId)));
    }

    /**
     * 미션 생성
     * [POST] /mission
     * 작성자 : 정승연
     */
    @PostMapping()
    public ResponseEntity<SuccessResponse<MissionCreateRes>> createMission(@RequestBody MissionReq missionReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_MISSION_SUCCESS.getMessage(), this.missionCreateUseCase.createMission(missionReq)));
    }

    /**
     * 미션 수정
     * [PUT] /mission/{missionId}
     * 작성자 : 정승연
     */
    @PutMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<MissionCreateRes>> updateMission(@PathVariable Long missionId, @RequestBody MissionReq missionReq) {
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_MISSION_SUCCESS.getMessage(), this.missionUpdateUseCase.updateMission(missionId, missionReq)));
    }

    /**
     * 미션 삭제
     * [DELETE] /mission/{missionId}
     * 작성자 : 정승연
     */
    @DeleteMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<Long>> deleteMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(DELETE_MISSION_SUCCESS.getMessage(), this.missionDeleteUseCase.deleteMission(missionId)));
    }


}
