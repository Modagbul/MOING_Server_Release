package com.moing.backend.domain.mission.presentation;

import com.moing.backend.domain.mission.application.dto.req.MissionCreateReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.CREATE_MISSION_SUCCESS;


@RestController
@AllArgsConstructor
@RequestMapping("/mission")
public class MissionController {


    private final MissionCreateUseCase missionCreateUseCase;
    private final MissionReadUseCase missionReadUseCase;

    /**
     * 미션 생성
     * [POST] /missions
     * 작성자 : 정승연
     */
    @PostMapping()
    public ResponseEntity<SuccessResponse<MissionCreateRes>> createMission(@RequestBody MissionCreateReq missionCreateReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_MISSION_SUCCESS.getMessage(), this.missionCreateUseCase.createMission(missionCreateReq)));
    }






}
